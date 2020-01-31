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

const questions = [];

for (let i = 0; i < 1000; i++) {
  questions.push({
    id: questions.length,
    text: `${i} + 1 = ?`
  });
}

let lastQN = 1000;
const broadcast = () =>
  wss.clients.forEach((client) => {
    lastQN++;
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(
        [1, 2].map(i => ({
          id: lastQN + i,
          text: `${lastQN + i} + 1 = ?`
        }))
      ));
    }
  });

setInterval(() => {
  broadcast();
}, 15000);

const router = new Router();
router.get('/question', ctx => {
  ctx.response.body = questions;
  ctx.response.status = 200;
});

router.post('/quiz', ctx => {
  const quiz = ctx.request.body;
  console.log(quiz)
  if (quiz && Array.isArray(quiz)) {
    let count = 0;
    quiz.forEach(r => {
      if(r.questionId + 1 == r.answer) {
        count++;
      }
    });
    ctx.response.body = { count };
    ctx.response.status = 200;
  } else {
    ctx.response.status = 400;
  }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
