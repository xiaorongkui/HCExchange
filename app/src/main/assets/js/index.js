$(document).ready(function() {

    // 安全保障切换

    $('.tab li').click(function() {
        var i = $(this).index(); //下标第一种写法
        //var i = $('tit').index(this);//下标第二种写法
        $(this).addClass('select').siblings().removeClass('select');
        $('.changes div').eq(i).show().siblings().hide();
        $(this).children().addClass('unline');
        $(this).siblings().children().removeClass('unline');
        $('.changes div').eq(i).show().siblings().hide();
    });

    // 进入风评

    $('.next').click(function() {
        window.location.href = "./survey.html"
    })

    // 所点中选项背景色

    $('.main div li').touchstart(function() {
        $num = $(this).index()
        $(this).addClass('bg')
    })

    // 题目切换

    $('#num1 li').click(function() {
        $('#num2').addClass('active')
        $('#num1').removeClass('active')
        $('.back').css({ "display": "block" })
    })
    $('#num2 li a').click(function() {
        $('#num3').addClass('active')
        $('#num2').removeClass('active')
    })
    $('#num3 li a').click(function() {
        $('#num4').addClass('active')
        $('#num3').removeClass('active')
    })
    $('#num4 li a').click(function() {
        $('#num5').addClass('active')
        $('#num4').removeClass('active')
    })
    $('#num5 li a').click(function() {
        $('#num6').addClass('active')
        $('#num5').removeClass('active')
    })
    $('#num6 li a').click(function() {
        $('#num7').addClass('active')
        $('#num6').removeClass('active')
    })
    $('#num7 li a').click(function() {
        $('#num8').addClass('active')
        $('#num7').removeClass('active')
    })
    $('#num8 li a').click(function() {
        $('#num9').addClass('active')
        $('#num8').removeClass('active')
    })
    $('#num9 li a').click(function() {
        $('#num10').addClass('active')
        $('#num9').removeClass('active')
    })

    // 进入风评结果页

    $('#num10 li a').click(function() {
        window.location.href = "./result.html"
    })

    // 风评重测

    $('#once').click(function() {
        window.location.href = "./survey.html"
    })

    // 返回上一题

    function tabBack() {
        $('.main div').eq($num).addClass('active').siblings().removeClass('active')
    }
    $('.tabNum').click(function() {
        $num--
        // if ($num < 0) {
        //     $num = $('.main div').length - 1

        // }
        $('.main div li').removeClass('bg')
        if ($num === 0) {
            $('.back').css({ "display": "none" })
        }
        alert($num)
        tabBack()
    });
})