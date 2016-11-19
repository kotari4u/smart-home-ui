<h2>
    Welcome
</h2>

<br/><br/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<script>
function switchControl(switchTermialNo, onOff) {
    var url = '/smarthome-ui/switchcontrol?switch='+switchTermialNo+'&control='+onOff;
    $.ajax({
      url: url,
      type: 'GET',
      datatype: 'json',
      success: function(response) { $("#subViewDiv").html( response ); },
      error: function() { $("#subViewDiv").html( 'error occured' ); }
    });
}
</script>


<button onclick="switchControl(3,'on')">Switch On - Terminal 3</button>
</br></br>
<button onclick="switchControl(3,'off')">Switch Off - Terminal 3</button>

<div id="subViewDiv"></div>