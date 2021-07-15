let inputRef = document.getElementById('archive')
let zipblob

inputRef.addEventListener('change', (evt) => {
    // Get filelist
    let files = evt.target.files

    // Assign new zip object
    let zip = new JSZip()

    // Loop through the filelist to get each filename and pass each file to zip object
    for(let file of files){
        let filename = file.name
        zip.file(filename, file)
    }

    // Generate the complete zip file
    zip.generateAsync({type:'blob'}).then((blobdata)=>{
        // create zip blob file
        zipblob = new Blob([blobdata])

        // For development and testing purpose
        // Download the zipped file 
        var elem = document.createElement("a")
        elem.setAttribute("id", "zip")
        elem.setAttribute("name", "zip")
        elem.href = URL.createObjectURL(zipblob)
        document.body.appendChild(elem);

        $.ajax({
            type: "POST",
            url : 'serv?command=index',
            data : {
                zip : $('#zip').id
            },
            success : function(response) {
                $('#ajaxUserServletResponse').val(response)
                alert('All files zipped!')
            }
        });
    })

})

function loadFunction(){

    console.log( $('#ajaxUserServletResponse').val())
    console.log( $('#zip').attr('href'))
    console.log( zipblob.toString())

    var file_object = fetch($('#zip').attr('href'))
        .then(r => r.blob())
        .then(blob => {
            var file_name = Math.random().toString(36).substring(6) + '_name.zip'; //e.g ueq6ge1j_name.pdf
            var file_object = new File([blob], file_name, {type: 'application/zip'});
            console.log(file_object); //Output
        });

    $.ajax({
        type: 'PUT',
        url: $('#ajaxUserServletResponse').val(),
        data: file_object,
        processData : false,
        headers: {'Content-Type': 'application/zip', 'x-amz-acl': 'public-read'},
        success: function() {
            alert('Successful upload!')
        },
        error: function() {
            alert('Unfortunate error!')
        }
    })
};