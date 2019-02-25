// One line code which does the same!
$(function(){
    $('#list li').addClass('pointer-cursor').click(function(){
        return $(this).children('ul').toggle('slow') && false;
    }).not(':has(ul)').addClass('default-cursor');
});