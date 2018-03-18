(function poll()
{
    setTimeout(function()
    {
        $.ajax
        ({
            url: "/doctorMeniu/poll",
            type: "POST",
            success: function(data)
            {
                console.log("polling");
                alert("Your next patient has arrived!");
            },
            dataType: "json",
            complete: poll,
            timeout: 2000
        })
    }, 5000);
})();