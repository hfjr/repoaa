var publicKeyUrl = "YOUR-DOMAIN/encryption-parameters";
var encryptUrl = "YOUR-DOMAIN/encryption-data";
var publicKey;

$('#getPublicKey').click(function(e){
  e.preventDefault();

  $('#publicKey').text("loading...");
  $.get(publicKeyUrl, function(result){
    console.log(result);
    if(result["publicKey"] != null){
      $('#publicKey').text(result["publicKey"]);
      publicKey = result["publicKey"];
    }
  })
  .fail(function() {
    alert("error");
  });
});

$("#encryption").click(function(e){
  e.preventDefault();
  var cardDetails = {};
  var encrypt = new JSEncrypt();
  encrypt.setPublicKey(publicKey);
  var encrypted = encrypt.encrypt(JSON.stringify(cardDetails));
  $("#encryptionResult").text(encrypted);
  $.ajax({
    type:"POST",
    url: encryptUrl,
    data:JSON.stringify({"encryptedData":encrypted}),
    contentType: 'application/json',
    dataType:"json",
    success:function(result){
      console.log("successful");
    }
  });
});
