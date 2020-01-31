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

const tableMap = {};

app.use(async (ctx, next) => {
  const token = ctx.request.headers['authorization'];
  if (ctx.request.url !== '/auth') {
    if (!token || !tableMap[token]) {
      ctx.response.status = 401;
      return;
    }
  }
  await next();
});

app.use(async (ctx, next) => {
  await new Promise(resolve => setTimeout(resolve, 1000));
  await next();
});

const menuItems = [];
let prefixChar = 'a';

const addMenuItems = () => {
  console.log(`add products ${prefixChar}...`);
  for (let i = 0; i < 10; i++) {
    menuItems.push({ code: menuItems.length + 1, name: `${prefixChar}0${i}`});
  }
  prefixChar = String.fromCharCode(prefixChar.charCodeAt(0) + 1);
};

for(let i = 0; i < 25; i++) {
  addMenuItems();
}

const broadcast = data =>
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });

setInterval(() => {
  broadcast(menuItems[Math.floor(Math.random() * menuItems.length)]);
}, 15000);

const router = new Router();

router.post('/auth', ctx => {
  const { table } = ctx.request.body;
  if (!table || tableMap[table]) {
    ctx.response.status = 400;
  } else {
    tableMap[table] = true;
    ctx.response.body = { token: table };
    ctx.response.status = 201;
  }
});

router.get('/MenuItem', ctx => {
  const { q } = ctx.request.query;
  if (!q || !q.length || q.length < 1) {
    ctx.response.body = { issue: 'q query param missing'};
    ctx.response.status = 400;
  } else {
    ctx.response.body = menuItems
      .filter(menuItem => menuItem.name.indexOf(q) !== -1);
    ctx.response.status = 200;
  }
});

const items = [];

router.post('/OrderItem', ctx => {
  const { code, quantity, free } = ctx.request.body;
  if (typeof code !== 'number' || menuItems.findIndex(p => p.code === code) === -1) {
    ctx.response.body = { issue: 'MenuItem code not found' };
    ctx.response.status = 400;
  } else {
    const token = ctx.request.headers['authorization'];
    const item = { id: items.length + 1, code, quantity, table: token, free };
    items.push(item);
    ctx.response.body = item;
    ctx.response.status = 200;
  }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
