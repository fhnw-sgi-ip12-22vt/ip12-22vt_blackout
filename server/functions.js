// init repl.it db
const Database = require("@replit/database");
const database = new Database();
const { translateText } = require('./translate')

async function getQueryStringFromQuestion(questionId) {
  let questions = await database.get('questions');
  let question = questions.filter(question => {
    return question.questionId == questionId;
  })
  return objectToQueryString(question[0]);
}

async function getQueryStringFromExponat(exponatId) {
  let exponats = await database.get('exponats');
  let exponat = exponats.filter(exponat => {
    return exponat.exponatId == exponatId;
  })
  return objectToQueryString(exponat[0]);
}

async function getQueryStringFromRoom(roomId) {
  let rooms = await database.get('rooms');
  let room = rooms.filter(room => {
    return room.roomId == roomId;
  })
  return objectToQueryString(room[0]);
}

function objectToQueryString(obj) {
  return Object.entries(obj)
    .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
    .join('&');
}

async function getLanguageAsJson() {
  let language = await database.get('language');
  return JSON.stringify(language);
}

async function getQuestionsAsJson(includeDeactivated) {
  let questions = await database.get('questions');
  if (!includeDeactivated) {
    questions = questions.filter(question => {
      return question.active;
    })
  }
  questions = questions.filter(question => {
    return question.exponatId != 'notSpecified';
  })
  return JSON.stringify({
    timestamp: new Date(),
    questions: questions
  });
}

async function getExponatsAsJson() {
  let exponats = await database.get('exponats');
  exponats.forEach(exponat => {
    exponat.label = exponat.exponatTitle;
    exponat.value = exponat.exponatId;
  })
  return JSON.stringify({
    timestamp: new Date(),
    exponats: exponats
  });
}

async function getRoomsAsJson() {
  let rooms = await database.get('rooms');
  rooms.forEach(room => {
    room.label = room.roomTitle;
    room.value = room.roomId;
  })
  return JSON.stringify({
    timestamp: new Date(),
    rooms: rooms
  });
}

async function createQuestion(params) {
  let {
    selectExponatId,
    questionString,
    answerString1,
    answer1,
    answerString2,
    answer2,
    answerString3,
    answer3,
    explanationString
  } = params;

  let translatedQuestionString = await translateText(questionString);
  let translatedAnswerString1 = await translateText(answerString1);
  let translatedAnswerString2 = await translateText(answerString2);
  let translatedAnswerString3 = await translateText(answerString3);
  let translatedExplanationString = await translateText(explanationString);

  let roomId;

  let exponats = await database.get('exponats');
  exponats.forEach(exponat => {
    if (exponat.exponatId == selectExponatId) {
      roomId = exponat.roomId;
    }
  })

  let question = {
    roomId: roomId,
    exponatId: selectExponatId,
    questionId: Math.floor(Math.random() * 5000000),
    questionStringEN: translatedQuestionString.en,
    questionStringDE: questionString,
    questionStringFR: translatedQuestionString.fr,
    questionStringIT: translatedQuestionString.it,
    answerString1EN: translatedAnswerString1.en,
    answerString1DE: answerString1,
    answerString1FR: translatedAnswerString1.fr,
    answerString1IT: translatedAnswerString1.it,
    answer1,
    answerString2EN: translatedAnswerString2.en,
    answerString2DE: answerString2,
    answerString2FR: translatedAnswerString2.fr,
    answerString2IT: translatedAnswerString2.it,
    answer2,
    answerString3EN: translatedAnswerString3.en,
    answerString3DE: answerString3,
    answerString3FR: translatedAnswerString3.fr,
    answerString3IT: translatedAnswerString3.it,
    answer3,
    answerStringsEN: [],
    answerStringsDE: [],
    answerStringsFR: [],
    answerStringsIT: [],
    solutionArray: [],
    explanationStringEN: translatedExplanationString.en,
    explanationStringDE: explanationString,
    explanationStringFR: translatedExplanationString.fr,
    explanationStringIT: translatedExplanationString.it,
    totalPoints: 0,
    maxPoints: 0,
    active: true
  };

  question.answerStringsEN.push(translatedAnswerString1.en, translatedAnswerString2.en, translatedAnswerString3.en);
  question.answerStringsDE.push(answerString1, answerString2, answerString3);
  question.answerStringsFR.push(translatedAnswerString1.fr, translatedAnswerString2.fr, translatedAnswerString3.fr);
  question.answerStringsIT.push(translatedAnswerString1.it, translatedAnswerString2.it, translatedAnswerString3.it);

  if (answer1 == "true") {
    question.solutionArray.push(0);
  }
  if (answer2 == "true") {
    question.solutionArray.push(1);
  }
  if (answer3 == "true") {
    question.solutionArray.push(2);
  }

  if (!await database.get('questions')) {
    await database.set('questions', []);
  }
  let questions = await database.get('questions');
  questions.push(question);
  await database.set('questions', questions);
}

async function updateQuestion(params) {
  let {
    selectExponatId,
    questionId,
    questionStringEN,
    questionStringDE,
    questionStringFR,
    questionStringIT,
    answerString1EN,
    answerString1DE,
    answerString1FR,
    answerString1IT,
    answer1,
    answerString2EN,
    answerString2DE,
    answerString2FR,
    answerString2IT,
    answer2,
    answerString3EN,
    answerString3DE,
    answerString3FR,
    answerString3IT,
    answer3,
    explanationStringEN,
    explanationStringDE,
    explanationStringFR,
    explanationStringIT
  } = params;

  let exponats = await database.get('exponats');
  exponats.forEach(exponat => {
    if (exponat.exponatId == selectExponatId) {
      roomId = exponat.roomId;
    }
  })

  let questions = await database.get('questions');
  if (!questions) throw new Error();
  let question = questions.filter(question => {
    return question.questionId == questionId;
  })
  if (!question) throw new Error();

  question[0].roomId = roomId;
  question[0].exponatId = selectExponatId;

  question[0].questionStringEN = questionStringEN;
  question[0].questionStringDE = questionStringDE;
  question[0].questionStringFR = questionStringFR;
  question[0].questionStringIT = questionStringIT;

  question[0].answerString1EN = answerString1EN;
  question[0].answerString1DE = answerString1DE;
  question[0].answerString1FR = answerString1FR;
  question[0].answerString1IT = answerString1IT;
  question[0].answer1 = answer1;

  question[0].answerString2EN = answerString2EN;
  question[0].answerString2DE = answerString2DE;
  question[0].answerString2FR = answerString2FR;
  question[0].answerString2IT = answerString2IT;
  question[0].answer2 = answer2;

  question[0].answerString3EN = answerString3EN;
  question[0].answerString3DE = answerString3DE;
  question[0].answerString3FR = answerString3FR;
  question[0].answerString3IT = answerString3IT;
  question[0].answer3 = answer3;

  question[0].answerStringsEN = [];
  question[0].answerStringsDE = [];
  question[0].answerStringsFR = [];
  question[0].answerStringsIT = [];

  question[0].solutionArray = [];

  question[0].answerStringsEN.push(answerString1EN, answerString2EN, answerString3EN);
  question[0].answerStringsDE.push(answerString1DE, answerString2DE, answerString3DE);
  question[0].answerStringsFR.push(answerString1FR, answerString2FR, answerString3FR);
  question[0].answerStringsIT.push(answerString1IT, answerString2IT, answerString3IT);

  if (answer1 == "true") {
    question[0].solutionArray.push(0);
  }
  if (answer2 == "true") {
    question[0].solutionArray.push(1);
  }
  if (answer3 == "true") {
    question[0].solutionArray.push(2);
  }

  question[0].explanationStringEN = explanationStringEN;
  question[0].explanationStringDE = explanationStringDE;
  question[0].explanationStringFR = explanationStringFR;
  question[0].explanationStringIT = explanationStringIT;

  let newQuestions = questions.filter(question => {
    return question.questionId != questionId;
  })
  newQuestions.push(question[0]);
  await database.set('questions', newQuestions);
}

async function createExponat(params) {
  let {
    selectRoomId,
    exponatTitle,
    exponatDescription
  } = params;

  if (selectRoomId == 'notSpecified') {
    throw new Error();
  }

  let exponat = {
    roomId: selectRoomId,
    exponatId: Math.floor(Math.random() * 3000000),
    exponatTitle,
    exponatDescription,
    totalPoints: 0,
    maxPoints: 0,
    active: true
  };

  if (!await database.get('exponats')) {
    await database.set('exponats', []);
  }
  let exponats = await database.get('exponats');
  exponats.push(exponat);
  await database.set('exponats', exponats);
}

async function updateExponat(params) {
  let {
    exponatId,
    selectRoomId,
    exponatTitle,
    exponatDescription
  } = params;

  let questions = await database.get('questions');
  if (!questions) throw new Error();

  questions.forEach(question => {
    if (question.exponatId == exponatId) {
      question.roomId = selectRoomId;
    }
  })

  await database.set('questions', questions);

  let exponats = await database.get('exponats');
  if (!exponats) throw new Error();
  let exponat = exponats.filter(exponat => {
    return exponat.exponatId == exponatId;
  })
  if (!exponat) throw new Error();

  exponat[0].roomId = selectRoomId;
  exponat[0].exponatTitle = exponatTitle;
  exponat[0].exponatDescription = exponatDescription;

  let newExponats = exponats.filter(exponat => {
    return exponat.exponatId != exponatId;
  })
  newExponats.push(exponat[0]);
  await database.set('exponats', newExponats);
}

async function createRoom(params) {
  let {
    roomTitle,
    roomDescription
  } = params;

  let room = {
    roomId: Math.floor(Math.random() * 1000000),
    roomTitle,
    roomDescription,
    totalPoints: 0,
    maxPoints: 0,
    active: true
  };

  if (!await database.get('rooms')) {
    await database.set('rooms', []);
  }
  let rooms = await database.get('rooms');
  rooms.push(room);
  await database.set('rooms', rooms);
}

async function updateRoom(params) {
  let {
    roomId,
    roomTitle,
    roomDescription
  } = params;

  let rooms = await database.get('rooms');
  if (!rooms) throw new Error();
  let room = rooms.filter(room => {
    return room.roomId == roomId;
  })
  if (!room) throw new Error();

  room[0].roomTitle = roomTitle;
  room[0].roomDescription = roomDescription;

  let newRooms = rooms.filter(room => {
    return room.roomId != roomId;
  })
  newRooms.push(room[0]);
  await database.set('rooms', newRooms);
}

async function deactivateQuestion(params) {
  let { questionId } = params;
  if (!questionId) throw new Error();

  let questions = await database.get('questions');
  if (!questions) throw new Error();
  let question = questions.filter(question => {
    return question.questionId == params.questionId;
  })
  if (!question) throw new Error();
  question[0].active = false;

  let newQuestions = questions.filter(question => {
    return question.questionId != questionId;
  })
  newQuestions.push(question[0]);
  await database.set('questions', newQuestions);
}

async function activateQuestion(params) {
  let { questionId } = params;
  if (!questionId) throw new Error();

  let questions = await database.get('questions');
  if (!questions) throw new Error();
  let question = questions.filter(question => {
    return question.questionId == params.questionId;
  })
  if (!question) throw new Error();
  question[0].active = true;

  let newQuestions = questions.filter(question => {
    return question.questionId != questionId;
  })
  newQuestions.push(question[0]);
  await database.set('questions', newQuestions);
}

async function deleteQuestion(params) {
  let { questionId } = params;
  if (!questionId) throw new Error();

  let questions = await database.get('questions');
  if (!questions) throw new Error();

  let newQuestions = questions.filter(question => {
    return question.questionId != questionId;
  })
  await database.set('questions', newQuestions);
}

async function activateExponat(params) {
  let { exponatId } = params;
  if (!exponatId) throw new Error();

  let exponats = await database.get('exponats');
  if (!exponats) throw new Error();
  let exponat = exponats.filter(exponat => {
    return exponat.exponatId == exponatId;
  })
  if (!exponat) throw new Error();
  exponat[0].active = true;

  let newExponats = exponats.filter(exponat => {
    return exponat.exponatId != exponatId;
  })
  newExponats.push(exponat[0]);
  await database.set('exponats', newExponats);
}

async function deactivateExponat(params) {
  let { exponatId } = params;
  if (!exponatId) throw new Error();

  let exponats = await database.get('exponats');
  if (!exponats) throw new Error();
  let exponat = exponats.filter(exponat => {
    return exponat.exponatId == exponatId;
  })
  if (!exponat) throw new Error();
  exponat[0].active = false;

  let newExponats = exponats.filter(exponat => {
    return exponat.exponatId != exponatId;
  })
  newExponats.push(exponat[0]);
  await database.set('exponats', newExponats);
}

async function deleteExponat(params) {
  let { exponatId } = params;
  if (!exponatId) throw new Error();

  let questions = await database.get('questions');
  if (!questions) throw new Error();

  questions.forEach(question => {
    if (question.exponatId == exponatId) {
      throw new Error();
    }
  })

  let exponats = await database.get('exponats');
  if (!exponats) throw new Error();

  let newExponats = exponats.filter(exponat => {
    return exponat.exponatId != exponatId;
  })
  await database.set('exponats', newExponats);
}

async function activateRoom(params) {
  let { roomId } = params;
  if (!roomId) throw new Error();

  let rooms = await database.get('rooms');
  if (!rooms) throw new Error();
  let room = rooms.filter(room => {
    return room.roomId == roomId;
  })
  if (!room) throw new Error();
  room[0].active = true;

  let newRooms = rooms.filter(room => {
    return room.roomId != roomId;
  })
  newRooms.push(room[0]);
  await database.set('rooms', newRooms);
}

async function deactivateRoom(params) {
  let { roomId } = params;
  if (!roomId) throw new Error();

  let rooms = await database.get('rooms');
  if (!rooms) throw new Error();
  let room = rooms.filter(room => {
    return room.roomId == roomId;
  })
  if (!room) throw new Error();
  room[0].active = false;

  let newRooms = rooms.filter(room => {
    return room.roomId != roomId;
  })
  newRooms.push(room[0]);
  await database.set('rooms', newRooms);
}

async function deleteRoom(params) {
  let { roomId } = params;
  if (!roomId) throw new Error();

  let questions = await database.get('questions');
  if (!questions) throw new Error();

  questions.forEach(question => {
    if (question.roomId == roomId) {
      throw new Error();
    }
  })

  let exponats = await database.get('exponats');
  if (!exponats) throw new Error();

  exponats.forEach(exponat => {
    if (exponat.roomId == roomId) {
      throw new Error();
    }
  })

  let rooms = await database.get('rooms');
  if (!rooms) throw new Error();

  let newRooms = rooms.filter(room => {
    return room.roomId != roomId;
  })
  await database.set('rooms', newRooms);
}

function getRandomNumber(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

module.exports = {
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
};
