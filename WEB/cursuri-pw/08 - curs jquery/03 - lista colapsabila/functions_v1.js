$(document).ready(function(){
    // Just show the pointer cursor for the expandable items.
    $('#list li').each(function(){
        if($(this).children('ul').length != 0) {
            $(this).addClass('pointer-cursor');
        } else {
            $(this).addClass('default-cursor');
        }
    });

    // Expand / collapse items
    $('#list li').click(function(){
        $(this).children('ul').toggle('slow');
        return false;
    });
});