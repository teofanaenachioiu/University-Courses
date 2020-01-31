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
app.use(async function (ctx, next) {
  const start = new Date();
  await next();
  const ms = new Date() - start;
  console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});

const notes = [];
for (let i = 0; i < 21; i++) {
  notes.push({id: i, text: `Note ${i}`, date: new Date(Date.now() + i)})
}
let lastUpdated = notes[notes.length - 1].date;
const pageSize = 10;

const router = new Router();
router.get('/note', ctx => {
  const ifModifiedSince = ctx.request.get('If-Modified-Since');
  if (ifModifiedSince && new Date(ifModifiedSince).getTime() >= lastUpdated.getTime() - lastUpdated.getMilliseconds()) {
    ctx.response.status = 304;
  } else {
    const page = parseInt(ctx.request.query.page) || 1;
    ctx.response.set('Last-Modified', lastUpdated.toUTCString());
    const sortedNotes = notes.sort((n1, n2) => -(n1.date.getTime() - n2.date.getTime()));
    const offset = (page - 1) * pageSize;
    ctx.response.body = {
      page,
      notes: sortedNotes.slice(offset, offset + pageSize),
      more: offset + pageSize < sortedNotes.length
    };
    ctx.response.status = 200;
  }
});

const broadcast = (data) =>
  wss.clients.forEach((client) => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
  });

router.del('/note/:id', ctx => {
  const id = parseInt(ctx.params.id);
  const index = notes.findIndex(note => id === note.id);
  if (index != -1) {
    const note = notes[index];
    notes.splice(index, 1);
    lastUpdated = new Date();
    broadcast({event: 'deleted', note});
  }
  ctx.response.status = 204;
});

setInterval(() => {
  lastUpdated = new Date();
  const note = {id: notes.length, text: `Note ${notes.length}`, date: lastUpdated}
  notes.push(note);
  console.log(`INSERTED ${note.text}`);
  broadcast({event: 'inserted', note});
}, 15000);

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
