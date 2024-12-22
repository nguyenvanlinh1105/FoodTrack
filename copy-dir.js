const fs = require('fs-extra');
const path = require('path');

// Async with promises:
fs.copy('views', 'dist/views')
  .then(() => console.log('success!'))
  .catch(err => console.error(err))

// Async with promises:
fs.copy('public', 'dist/public')
  .then(() => console.log('success!'))
  .catch(err => console.error(err))

// Async with async/await:
const nodeModules=path.join(__dirname, 'node_modules', 'tinymce');
fs.copy(nodeModules, 'dist/node_modules/tinymce')
  .then(() => console.log('success!'))
  .catch(err => console.error(err))