# Documentation

## Integration Tests

There's 3 tests, stored at `src/test/kotlin`, that have been made for this Kotlin Webpage

---

### HTML/CSS Tests

The file `src/test/kotlin/IntegrationTest.kt` contains two tests that checks the main behaivour of the HTML page itself:

- `testHome()` checks if making a request at `http://localhost:$port` (With `$port` in this case being 0 for the shake of the test), yields both:

  - A `OK` HTTP Status Code.
  - A HTML body with `<title>hello`.

    If this happens, we can assume the webpage's HTML is the one intended.
- `testCss()` checks if the CSS of the webpage has basic functionality. For this, it request `http://localhost:$port/webjars/bootstrap/5.1.0/css/bootstrap.min.css` from the Web Server, and checks if it has a response with:

  - A `OK` HTML Status.
  - A body with `"body"`.
  - A file with a header equal to that of `"text/css"`.

This ensures the webpage has a valid CSS file.
