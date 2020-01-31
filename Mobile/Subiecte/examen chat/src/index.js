const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');

app.use(bodyparser());
app.use(cors());

app.use(async (ctx, next) => {
  const start = new Date();
  await next();
  const ms = new Date() - start;
  console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});

app.use(async (ctx, next) => {
  await new Promise(resolve => setTimeout(resolve, 1000));
  await next();
});

function Message(id, text, username, room) {
  this.id = id;
  this.text = text;
  this.created = Date.now();
  this.username = username;
  this.room = room;
}

const tokenMap = { u1: 'u1' };
const messages = [
  new Message(0, 'm0', 'u1', 'r0'),
  new Message(1, 'm1', 'u1', 'r1')
];

app.use(async (ctx, next) => {
  const token = ctx.request.headers['token'];
  console.log('token', token, tokenMap[token]);
  if (!tokenMap[token]) {
    if (ctx.request.url === '/login') {
      const username = ctx.request.body.username;
      if (username && !tokenMap[username]) {
        tokenMap[username] = username;
        ctx.response.body = { token: username };
        ctx.response.status = 201;
      } else {
        ctx.response.status = 400;
      }
    } else {
      ctx.response.status = 401;
    }
    return;
  }
  await next();
});

const router = new Router();

router.get('/message', ctx => {
  const created = parseInt(ctx.request.query.created || '0');
  if (created === 0) {
    console.log('created query param undefined');
  }
  ctx.response.body = messages.filter(message => message.created >= created);
  ctx.response.status = 200;
});

const broadcast = (data) =>
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });

router.post('/message', ctx => {
  const text = ctx.request.body.text;
  const room = ctx.request.body.room;
  const message = new Message(messages.length, text, ctx.request.headers['token'], room);
  messages.push(message);
  ctx.response.body = message;
  ctx.response.status = 200;
  broadcast(message);
});

let count = 0;
setInterval(() => {
  const id = messages.length;
  const message = new Message(id, `m${id}`, 'u1', `r${count}`);
  messages.push(message);
  console.log(`broadcast ${message.text} to ${message.room}`);
  broadcast(message);
  count = ++count % 2;
}, 5000);

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
