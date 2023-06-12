(function(){
  const responseArea = document.querySelector("#paymentResponse")

  function submitButtonClick(event) {
    event.preventDefault();
    sendRequest(document.querySelector("form#paymentForm"))
  }
  function submitForm(e){
    sendRequest(e.target)
  }
  function sendRequest(form){
    const start = new Date();

    const data = new FormData(form);
    const req = {};
    data.forEach( (v, k) =>  req[k] = v  )
    console.log(JSON.stringify(req));
    fetch("/api/v1/payments", {
      method : "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(req)
    })
    .then (raw => raw.json())
    .then((json) => {
      const end = new Date();
      const dur = ("" + (end.getMilliseconds() - start.getMilliseconds())).padStart(4,0);
      console.log(json);
      const e = document.createElement("pre");

      e.innerText = `[${start.toISOString()} ${dur}ms] - [${json.status}] ${json.transactionId} ${json.description}`;
      responseArea.appendChild(e);
    })
  }
  const submitButton = document.querySelector("button#submit");
  submitButton.addEventListener('click', submitButtonClick);
  const paymentForm = document.querySelector("form#paymentForm")
  paymentForm.addEventListener("submit", submitForm);

})()
