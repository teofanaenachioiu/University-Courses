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

const products = [];
let prefixChar = 'a';

const addProducts = () => {
  console.log(`add products ${prefixChar}...`);
  for (let i = 0; i < 10; i++) {
    products.push({ code: products.length + 1, name: `${prefixChar}0${i}`});
  }
  prefixChar = String.fromCharCode(prefixChar.charCodeAt(0) + 1);
};

addProducts();
addProducts();

const broadcast = event =>
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify({ event }));
    }
  });

setInterval(() => {
  if (prefixChar >= 'z') {
    return;
  }
  addProducts();
  broadcast('productsChanged');
}, 15000);

const pageSize = 10;

const router = new Router();
router.get('/product', ctx => {
  const page = parseInt(ctx.request.query.page) || 1;
  const offset = (page - 1) * pageSize;
  ctx.response.body = {
    total: products.length,
    page,
    products: products.slice(offset, offset + pageSize),
  };
  ctx.response.status = 200;
});

const items = [];

router.post('/item', ctx => {
  const { code, quantity } = ctx.request.body;
  if (typeof code !== 'number' || products.findIndex(p => p.code === code) === -1) {
    ctx.response.body = { issue: 'Product code not found' };
    ctx.response.status = 400;
  } else {
    const item = { id: items.length + 1, code, quantity };
    items.push(item);
    ctx.response.body = item;
    ctx.response.status = 200;
  }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
