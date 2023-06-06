const {
  getQueryStringFromQuestion,
  getQueryStringFromExponat,
  getQueryStringFromRoom,
  objectToQueryString,
  getLanguageAsJson,
  getQuestionsAsJson,
  getExponatsAsJson,
  getRoomsAsJson,
  createQuestion,
  updateQuestion,
  createExponat,
  updateExponat,
  createRoom,
  updateRoom,
  deactivateQuestion,
  activateQuestion,
  deleteQuestion,
  activateExponat,
  deactivateExponat,
  deleteExponat,
  activateRoom,
  deactivateRoom,
  deleteRoom,
  getRandomNumber
} = require('./functions');
const language = require('./language.json');
const { exportDatabase } = require('./export');

// init repl.it db
const Database = require("@replit/database");
const database = new Database();

// init express server
const express = require('express');
const path = require('path');
const app = express();
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.listen(3000, () => console.log('server started'));

// BETA BETA BETA

app.get('/export', async (req, res) => {
  res.json(await exportDatabase());
})

// app.post('/import', async (req, res) => {
//   await importDatabase(req.body);
//   res.send('OK');
// })

// app.get('/mjolnir', async (req, res) => {
//   await database.empty();
//   res.send('mjolnir');
// })

// BETA BETA BETA

function secureRoute(password) {
  if (password != process.env.PASSWORD) throw new Error();
}

app.use(async (req, res, next) => {
  try {
    if (!await database.get('language')) {
      await database.set('language', language)
    }
    if (!await database.get('questions')) {
      await database.set('questions', [])
    }
    if (!await database.get('exponats')) {
      await database.set('exponats', [])
    }
    if (!await database.get('rooms')) {
      await database.set('rooms', [])
    }
    if (!await database.get('stats')) {
      await database.set('stats', [])
    }
    next();
  } catch (error) {
    next();
  }
})

// GET index.html
app.get('/', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/index.html');
  } catch (error) {
    res.sendFile(__dirname + '/index.html');
  }
})

app.get('/index', async (req, res) => {
  res.redirect('/');
})

app.get('/index.html', async (req, res) => {
  res.redirect('/');
})

// GET list-questions.html
app.get('/list-questions', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/list-questions.html');
  } catch (error) {
    let url = '/error?redirectURL=/list?password=' + p;
    res.redirect(url);
  }
});

app.get('/list-questions.html', async (req, res) => {
  res.redirect('/list-questions');
});

// GET list-exponats.html
app.get('/list-exponats', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/list-exponats.html');
  } catch (error) {
    let url = '/error?redirectURL=/list?password=' + p;
    res.redirect(url);
  }
});

app.get('/list-exponats.html', async (req, res) => {
  res.redirect('/list-exponats');
});

// GET list-rooms.html
app.get('/list-rooms', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/list-rooms.html');
  } catch (error) {
    let url = '/error?redirectURL=/list?password=' + p;
    res.redirect(url);
  }
});

app.get('/list-rooms.html', async (req, res) => {
  res.redirect('/list-rooms');
});

// GET create-question.html
app.get('/create-question', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/create-question.html');
  } catch (error) {
    let url = '/error?redirectURL=/list?password=' + p;
    res.redirect(url);
  }
});

app.get('/create-question.html', async (req, res) => {
  res.redirect('/create-question');
});

// GET create-exponat.html
app.get('/create-exponat', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/create-exponat.html');
  } catch (error) {
    let url = '/error?redirectURL=/list?password=' + p;
    res.redirect(url);
  }
});

app.get('/create-exponat.html', async (req, res) => {
  res.redirect('/create-exponat');
});

// GET create-room.html
app.get('/create-room', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/create-room.html');
  } catch (error) {
    let url = '/error?redirectURL=/list?password=' + p;
    res.redirect(url);
  }
});

app.get('/create-room.html', async (req, res) => {
  res.redirect('/create-room');
});

// POST create-question.html
app.post('/create-question', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let data = req.body;
    await createQuestion(data);
    let url = '/success?redirectURL=/list-questions?password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-questions?password=' + p;
    res.redirect(url);
  }
});

app.post('/create-question.html', async (req, res) => {
  res.redirect('/create-question');
});

// POST create-exponat.html
app.post('/create-exponat', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let data = req.body;
    await createExponat(data);
    let url = '/success?redirectURL=/list-exponats?password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-exponats?password=' + p;
    res.redirect(url);
  }
});

app.post('/create-exponat.html', async (req, res) => {
  res.redirect('/create-exponat');
});

// POST create-room.html
app.post('/create-room', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let data = req.body;
    await createRoom(data);
    let url = '/success?redirectURL=/list-rooms?password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-rooms?password=' + p;
    res.redirect(url);
  }
});

app.post('/create-room.html', async (req, res) => {
  res.redirect('/create-room');
});

// GET do-update-question
app.get('/do-update-question', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let qI = req.query.questionId;
    let qS = await getQueryStringFromQuestion(qI);
    let url = '/update-question?' + qS + '&password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-questions?password=' + p;
    res.redirect(url);
  }
});

app.get('/do-update-question.html', async (req, res) => {
  res.redirect('/do-update-question');
});

// GET do-update-exponat
app.get('/do-update-exponat', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let eI = req.query.exponatId;
    let qS = await getQueryStringFromExponat(eI);
    let url = '/update-exponat?' + qS + '&password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-exponats?password=' + p;
    res.redirect(url);
  }
});

app.get('/do-update-exponat.html', async (req, res) => {
  res.redirect('/do-update-exponat');
});

// GET do-update-room
app.get('/do-update-room', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let rI = req.query.roomId;
    let qS = await getQueryStringFromRoom(rI);
    let url = '/update-room?' + qS + '&password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-rooms?password=' + p;
    res.redirect(url);
  }
});

app.get('/do-update-room.html', async (req, res) => {
  res.redirect('/do-update-room');
});

// GET update-question
app.get('/update-question', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/update-question.html');
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-questions?password=' + p;
    res.redirect(url);
  }
});

app.get('/update-question.html', async (req, res) => {
  res.redirect('/update-question');
});

// GET update-exponat
app.get('/update-exponat', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/update-exponat.html');
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-exponats?password=' + p;
    res.redirect(url);
  }
});

app.get('/update-exponat.html', async (req, res) => {
  res.redirect('/update-exponat');
});

// GET update-room
app.get('/update-room', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    res.sendFile(__dirname + '/update-room.html');
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-rooms?password=' + p;
    res.redirect(url);
  }
});

app.get('/update-room.html', async (req, res) => {
  res.redirect('/update-room');
});

// POST update-question
app.post('/update-question', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let data = req.body;
    await updateQuestion(data);
    let url = '/success?redirectURL=/list-questions?password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-questions?password=' + p;
    res.redirect(url);
  }
});

app.post('/update-question.html', async (req, res) => {
  res.redirect('/update-question');
})

// POST update-exponat
app.post('/update-exponat', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let data = req.body;
    await updateExponat(data);
    let url = '/success?redirectURL=/list-exponats?password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-exponats?password=' + p;
    res.redirect(url);
  }
});

app.post('/update-exponat.html', async (req, res) => {
  res.redirect('/update-exponat');
})

// POST update-room
app.post('/update-room', async (req, res) => {
  let p = req.query.password;
  try {
    secureRoute(p);
    let data = req.body;
    await updateRoom(data);
    let url = '/success?redirectURL=/list-rooms?password=' + p;
    res.redirect(url);
  } catch (error) {
    console.log(error);
    let url = '/error?redirectURL=/list-rooms?password=' + p;
    res.redirect(url);
  }
});

app.post('/update-room.html', async (req, res) => {
  res.redirect('/update-room');
})

// GET success
app.get('/success', async (req, res) => {
  res.sendFile(__dirname + '/success.html');
});

app.get('/success.html', async (req, res) => {
  res.sendFile(__dirname + '/success.html');
});

// GET error
app.get('/error', async (req, res) => {
  res.sendFile(__dirname + '/error.html');
});

app.get('/error.html', async (req, res) => {
  res.sendFile(__dirname + '/error.html');
});

// GET delete-question
app.get('/delete-question', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await deleteQuestion(req.query);
    res.sendFile(__dirname + "/list-questions.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET delete-exponat
app.get('/delete-exponat', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await deleteExponat(req.query);
    res.sendFile(__dirname + "/list-exponats.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET delete-room
app.get('/delete-room', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await deleteRoom(req.query);
    res.sendFile(__dirname + "/list-rooms.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET deactivate-question
app.get('/deactivate-question', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await deactivateQuestion(req.query);
    res.sendFile(__dirname + "/list-questions.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET activate-question
app.get('/activate-question', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await activateQuestion(req.query);
    res.sendFile(__dirname + "/list-questions.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET deactivate-exponat
app.get('/deactivate-exponat', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await deactivateExponat(req.query);
    res.sendFile(__dirname + "/list-exponats.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET activate-exponat
app.get('/activate-exponat', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await activateExponat(req.query);
    res.sendFile(__dirname + "/list-exponats.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET deactivate-room
app.get('/deactivate-room', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await deactivateRoom(req.query);
    res.sendFile(__dirname + "/list-rooms.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET activate-room
app.get('/activate-room', async (req, res) => {
  try {
    let p = req.query.password;
    secureRoute(p);
    await activateRoom(req.query);
    res.sendFile(__dirname + "/list-rooms.html");
  } catch (error) {
    console.log(error);
    res.sendFile(__dirname + "/error.html");
  }
});

// GET language.json
app.get('/language.json', async (req, res) => {
  let language = require('./language.json');
  res.json(language);
});

// GET questions.json
app.get('/questions.json', async (req, res) => {
  let questions = await getQuestionsAsJson();
  res.json(JSON.parse(questions))
});

// GET exponats.json
app.get('/exponats.json', async (req, res) => {
  let exponats = await getExponatsAsJson();
  res.json(JSON.parse(exponats))
});

// GET rooms.json
app.get('/rooms.json', async (req, res) => {
  let rooms = await getRoomsAsJson();
  res.json(JSON.parse(rooms))
});

// GET report
app.get('/report', async (req, res) => {
  let stats = await database.get('stats');
  let alreadySaved = false;
  stats.forEach(stat => {
    if (stat.date == req.query.date) {
      alreadySaved = true;
    }
  })
  if (!alreadySaved) {
    stats.push(req.query);
  }
  await database.set('stats', stats);
  res.sendFile(__dirname + '/report.html');
})

// GET select-language.html
app.get('/select-language', async (req,res) => {
  res.sendFile(__dirname + '/select-language.html');
})

// GET get-random-question.html
app.get('/get-random-question', async (req,res) => {
  let roomId = req.query.roomId;
  
  let questions = await database.get('questions');
  if (!questions) throw new Error();

  let possibleQuestions = questions.filter(question => {
    return question.roomId == roomId;
  })

  let randomQuestion = possibleQuestions[getRandomNumber(0, possibleQuestions.length - 1)];

  let url = 'random-question?' 
    + await getQueryStringFromQuestion(randomQuestion.questionId)
    + await getQueryStringFromExponat(randomQuestion.exponatId)
    + await getQueryStringFromRoom(randomQuestion.roomId);
  
  res.redirect(url);
})

// GET random-question.html
app.get('/random-question', async (req,res) => {
  res.sendFile(__dirname + '/random-question.html');
})

app.use(express.static(path.join(__dirname)));
app.use((req, res) => {
  res.status(404).sendFile(__dirname + '/error.html');
});
