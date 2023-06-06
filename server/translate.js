const axios = require('axios');
const apiKey = process.env.DEEPL_API_KEY;

async function translateText(text) {
  try {
    const translations = await Promise.all([
      translate(text, 'DE', 'EN'),
      translate(text, 'DE', 'FR'),
      translate(text, 'DE', 'IT'),
    ]);

    const results = {
      en: translations[0],
      de: text,
      fr: translations[1],
      it: translations[2],
    };

    return results;
  } catch (error) {
    console.error('Fehler bei der Übersetzung:', error);
  }
}

async function translate(text, sourceLang, targetLang) {
  const apiUrl = 'https://api-free.deepl.com/v2/translate';

  try {
    const response = await axios.post(apiUrl, null, {
      params: {
        auth_key: apiKey,
        text: text,
        source_lang: sourceLang,
        target_lang: targetLang,
      },
    });

    return response.data.translations[0].text;
  } catch (error) {
    console.error('Fehler bei der Übersetzung:', error);
  }
}

module.exports = { translateText };