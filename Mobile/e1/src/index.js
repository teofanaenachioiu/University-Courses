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
  console.log(`${ctx.method} ${ctx.url} - ${ms}ms`);
});

app.use(async (ctx, next) => {
  await new Promise(resolve => setTimeout(resolve, 1000));
  await next();
});

const messages = [];

function addMessageToUser(userIndex) {
  const username = `u${userIndex}`;
  const messageIndex = messages.length;
  messages.push({
    id: messageIndex,
    text: `m${messageIndex} to ${username}?`,
    read: false,
    sender: username,
    created: Date.now()
  });
  console.log(`Added m${messageIndex} to ${username}`);
}

for (let i = 0; i < 100; i++) {
  addMessageToUser(i % 10, messages.length);
}

let userIndex = 0;
const broadcast = () => {
  addMessageToUser(userIndex);
  userIndex++;
  if (userIndex > 9) {
    userIndex = userIndex % 10;
  }
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(messages[messages.length - 1]));
    }
  });
};

setInterval(() => {
  broadcast();
}, 5000);

const router = new Router();
router.get('/message', ctx => {
  ctx.response.body = messages;
  ctx.response.status = 200;
});

router.put('/message/:id', ctx => {
  const message = ctx.request.body;
  const id = parseInt(ctx.params.id);
  const index = messages.findIndex(msg => msg.id === id);
  if (id !== message.id || index === -1) {
    ctx.response.body = {text: 'Message not found'};
    ctx.response.status = 400;
  } else {
    messages[index].read = message.read;
    ctx.response.body = messages[index];
    ctx.response.status = 200;
  }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
