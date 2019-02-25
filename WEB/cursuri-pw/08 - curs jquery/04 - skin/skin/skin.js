(function($){
    $.fn.initComboSkin = function() {
        this.hide();
        this.wrap('<div style="text-align: left; position: relative; width: 80px; height: 28px; background: url(skin/skin_back_80.gif) no-repeat">');
        this.before('<label style="position: absolute; top: 4px; left: 5px"></label>');
        this.before('<img src="skin/arrow_down.gif" style="position: absolute; top: 6px; right: 5px; vertical-align: middle; cursor: pointer" alt=""/>');
        this.before('<ul class="combo"></ul>');

        var select = this;
        var container = this.parent();
        var combo = container.children("ul");

        this.children("option").each(function() {
            combo.append('<li>' + this.text + '</li>');
        });

        combo.css("position", "absolute");
        combo.css("top", "28px");

        container.find("label").html(this.find("option:selected").html());

        container.click(function(event) {
            if (! combo.is(":visible"))
                $(".combo").hide();
            combo.slideToggle('fast', function() {
                if (combo.is(":visible"))
                    container.css("background-image", "url(skin/skin_back2_80.gif)");
                else
                    container.css("background-image", "url(skin/skin_back_80.gif)")
            });
        });
        container.find("img").mousedown(function() {
            $(this).attr("src", "skin/arrow_down2.gif");
        });
        container.find("img").mouseup(function() {
            $(this).attr("src", "skin/arrow_down.gif");
        });
        combo.children("li").mouseover(function() {
            $(this).css("background-color", "#aabcd3");
        });
        combo.children("li").mouseout(function() {
            $(this).css("background-color", "white");
        });
        combo.children("li").click(function() {
            container.find("label").html($(this).html());
            select[0].selectedIndex = $(this).index();
        });
    };

})(jQuery);
