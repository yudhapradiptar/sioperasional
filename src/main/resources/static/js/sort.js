function sort_kategori()
{
    var table=$('#mytable');
    var tbody =$('#table1');

    tbody.find('tr').sort(function(a, b)
    {
        if($('#kategori_order').val()=='asc')
        {
            return $('td:first', a).text().localeCompare($('td:first', b).text());
        }
        else
        {
            return $('td:first', b).text().localeCompare($('td:first', a).text());
        }

    }).appendTo(tbody);

    var sort_order=$('#kategori_order').val();
    if(sort_order=="asc")
    {
        document.getElementById("kategori_order").value="desc";
    }
    if(sort_order=="desc")
    {
        document.getElementById("kategori_order").value="asc";
    }
}

function sort_id()
{
    var table=$('#mytable');
    var tbody =$('#table1');

    tbody.find('tr').sort(function(a, b)
    {
        if($('#id_order').val()=='asc')
        {
            return $('td:last', a).text().localeCompare($('td:last', b).text());
        }
        else
        {
            return $('td:last', b).text().localeCompare($('td:last', a).text());
        }

    }).appendTo(tbody);

    var sort_order=$('#id_order').val();
    if(sort_order=="asc")
    {
        document.getElementById("id_order").value="desc";
        console.log("jalan ga");
    }
    if(sort_order=="desc")
    {
        document.getElementById("id_order").value="asc";
    }
}