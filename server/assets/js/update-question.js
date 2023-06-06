const selectExponatId = document.querySelector('select[name="selectExponatId"]');

const questionStringEN = document.querySelector('input[name="questionStringEN"]');
const questionStringDE = document.querySelector('input[name="questionStringDE"]');
const questionStringFR = document.querySelector('input[name="questionStringFR"]');
const questionStringIT = document.querySelector('input[name="questionStringIT"]');

const answer1StringEN = document.querySelector('input[name="answerString1EN"]');
const answer1StringDE = document.querySelector('input[name="answerString1DE"]');
const answer1StringFR = document.querySelector('input[name="answerString1FR"]');
const answer1StringIT = document.querySelector('input[name="answerString1IT"]');
const answer1 = document.querySelector('select[name="answer1"]');

const answer2StringEN = document.querySelector('input[name="answerString2EN"]');
const answer2StringDE = document.querySelector('input[name="answerString2DE"]');
const answer2StringFR = document.querySelector('input[name="answerString2FR"]');
const answer2StringIT = document.querySelector('input[name="answerString2IT"]');
const answer2 = document.querySelector('select[name="answer2"]');

const answer3StringEN = document.querySelector('input[name="answerString3EN"]');
const answer3StringDE = document.querySelector('input[name="answerString3DE"]');
const answer3StringFR = document.querySelector('input[name="answerString3FR"]');
const answer3StringIT = document.querySelector('input[name="answerString3IT"]');
const answer3 = document.querySelector('select[name="answer3"]');

const explanationStringEN = document.querySelector('input[name="explanationStringEN"]');
const explanationStringDE = document.querySelector('input[name="explanationStringDE"]');
const explanationStringFR = document.querySelector('input[name="explanationStringFR"]');
const explanationStringIT = document.querySelector('input[name="explanationStringIT"]');

function getQueryParameterValue(key) {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  return urlParams.get(key);
}

const form = document.querySelector('#form');
const hiddenInput = document.createElement('input');
hiddenInput.type = 'hidden';
hiddenInput.name = 'questionId';
hiddenInput.value = getQueryParameterValue('questionId');
form.appendChild(hiddenInput);

fetch('exponats.json')
  .then((response) => response.json())
  .then((data) => {
    if (!data.exponats) return;
    fillSelectWithOptions(data.exponats);
  })
  .catch((error) => {
    console.log(error);
    setTimeout(() => {
      window.location.href = '/error.html';
    }, 3000);
  })

function fillSelectWithOptions(options) {
  options.forEach(option => {
    const optionElement = document.createElement('option');
    optionElement.value = option.value;
    optionElement.text = option.label;
    selectExponatId.appendChild(optionElement);
  });
  selectExponatId.value = getQueryParameterValue('exponatId');
}

function fillFormFields() {
  selectExponatId.value = getQueryParameterValue('locationId');

  questionStringEN.value = getQueryParameterValue('questionStringEN');
  questionStringDE.value = getQueryParameterValue('questionStringDE');
  questionStringFR.value = getQueryParameterValue('questionStringFR');
  questionStringIT.value = getQueryParameterValue('questionStringIT');

  answer1StringEN.value = getQueryParameterValue('answerString1EN');
  answer1StringDE.value = getQueryParameterValue('answerString1DE');
  answer1StringFR.value = getQueryParameterValue('answerString1FR');
  answer1StringIT.value = getQueryParameterValue('answerString1IT');
  answer1.value = getQueryParameterValue('answer1');

  answer2StringEN.value = getQueryParameterValue('answerString2EN');
  answer2StringDE.value = getQueryParameterValue('answerString2DE');
  answer2StringFR.value = getQueryParameterValue('answerString2FR');
  answer2StringIT.value = getQueryParameterValue('answerString2IT');
  answer2.value = getQueryParameterValue('answer2');
  
  answer3StringEN.value = getQueryParameterValue('answerString3EN');
  answer3StringDE.value = getQueryParameterValue('answerString3DE');
  answer3StringFR.value = getQueryParameterValue('answerString3FR');
  answer3StringIT.value = getQueryParameterValue('answerString3IT');
  answer3.value = getQueryParameterValue('answer3');
  
  explanationStringEN.value = getQueryParameterValue('explanationStringEN');
  explanationStringDE.value = getQueryParameterValue('explanationStringDE');
  explanationStringFR.value = getQueryParameterValue('explanationStringFR');
  explanationStringIT.value = getQueryParameterValue('explanationStringIT');
}

fillFormFields();