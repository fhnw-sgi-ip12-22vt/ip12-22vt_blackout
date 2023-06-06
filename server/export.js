async function exportDatabase() {
  const Database = require("@replit/database");
  const database = new Database();
  return await database.getAll();
}

async function importDatabase(dbAsJson) {
  const Database = require("@replit/database");
  const database = new Database();
  await database.empty();
  Object.keys(dbAsJson).forEach(async (key) => {
    await database.set(key, dbAsJson[key]);
  })
  return true;
}

module.exports = { exportDatabase, importDatabase };