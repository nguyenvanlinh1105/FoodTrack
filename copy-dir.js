const fs = require('fs-extra');

// Async with promises:
fs.copy('views', 'dist/views')
  .then(() => console.log('success!'))
  .catch(err => console.error(err))

// Async with promises:
fs.copy('public', 'dist/public')
  .then(() => console.log('success!'))
  .catch(err => console.error(err))