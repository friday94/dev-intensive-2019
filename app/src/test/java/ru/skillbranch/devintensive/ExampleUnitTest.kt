package ru.skillbranch.devintensive

import android.app.Activity
import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.*
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user2 = User("2", "John", "Cena")
    }

    @Test
    fun test_factory() {
//        val user = User.makeUser("John Cena")
//        val user2 = User.makeUser("John Wick")
        val user = User.makeUser("John")
        val user2 = user.copy(id = "2", lastName = "Cena", lastVisit = Date())
        print("$user \n$user2")
    }

    @Test
    fun test_decomposition() {
        val user = User.makeUser("John Wick")

        fun getUserInfo() = user

        val (id, firstName, lastName) = getUserInfo()

        println("$id, $firstName, $lastName")
        println("${user.component1()}, ${user.component2()}, ${user.component3()}")
    }

    @Test
    fun test_copy() {
        val user = User.makeUser("John Wick")
        var user2 = user.copy(lastVisit = Date())
        var user3 = user.copy(lastVisit = Date().add(-2, TimeUnits.SECOND))
        var user4 = user.copy(lastName = "Cena", lastVisit = Date().add(2, TimeUnits.HOUR))

        println(
            """
            ${user.lastVisit?.format()}
            ${user2.lastVisit?.format()}
            ${user3.lastVisit?.format()}
            ${user4.lastVisit?.format()}
        """.trimIndent()
        )

    }

    @Test
    fun test_dataq_maping() {
        val user = User.makeUser("Конусевич Дмитрий")
        val newUser = user.copy(lastVisit = Date().add(-800, TimeUnits.DAY))
        println(newUser)
        val userView = user.toUseView()
        userView.printMe()
    }

    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("Конусевич Дмитрий")
        val txtMessage = BaseMessage.makeMessage(
            user,
            Chat("0"),
            date = Date().add(-22, TimeUnits.HOUR),
            payload = "any text message",
            type = "text"
        )
        val imgMessage = BaseMessage.makeMessage(
            user,
            Chat("0"),
            date = Date().add(-30, TimeUnits.SECOND),
            payload = "any image url",
            type = "image"
        )

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())
    }

    @Test
    fun test_builder_user() {
        val user = User.Builder()
            .id("1")
            .firstName("Дмитрий")
            .lastName("Конусевич")
            .avatar("Картинка URL")
            .rating(5)
            .respect(2)
            .lastVisit(Date().add(-2, TimeUnits.DAY))
            .isOnline(false)
            .build()
        println(user)
    }

    @Test
    fun test_date_humanize() {
        println(Date().add(-5, TimeUnits.HOUR).humanizeDiff())
        println(Date().add(2, TimeUnits.HOUR).humanizeDiff())
        println(Utils.transliteration("Amazing Петр", "_"))
    }

    @Test
    fun transliteration() {
        assertEquals("Ivan Stereotipov", Utils.transliteration("Иван Стереотипов"))
        assertEquals("Amazing_Petr", Utils.transliteration("Amazing Петр", "_"))

        assertEquals("Zh Zh", Utils.transliteration("Ж Ж"))
        assertEquals("ZhZh", Utils.transliteration("ЖЖ"))
        assertEquals("AbrAKadabra", Utils.transliteration("AbrAKadabra"))
        assertEquals("StraNNIi NikVash'e", Utils.transliteration("СтраННЫй НикВаще"))
    }

    @Test
    fun test_humanizeDiff() {
        assertEquals("через несколько секунд", Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND)))
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())

        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("5 минут назад", Date().add(-5, TimeUnits.MINUTE).humanizeDiff())

        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())

        assertEquals("день назад", Date().add(-1, TimeUnits.DAY).humanizeDiff())
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("100 дней назад", Date().add(-100, TimeUnits.DAY).humanizeDiff())

        assertEquals("несколько секунд назад", Date().humanizeDiff(Date().add(34, TimeUnits.SECOND)))
        assertEquals("минуту назад", Date().humanizeDiff(Date().add(61, TimeUnits.SECOND)))
        assertEquals("5 минут назад", Date().humanizeDiff(Date().add(5, TimeUnits.MINUTE)))
        assertEquals("20 дней назад", Date().humanizeDiff(Date().add(20, TimeUnits.DAY)))
        assertEquals("90 дней назад", Date().humanizeDiff(Date().add(90, TimeUnits.DAY)))
        assertEquals("через несколько секунд", Date().humanizeDiff(Date().add(-13, TimeUnits.SECOND)))
        assertEquals("через минуту", Date().humanizeDiff(Date().add(-63, TimeUnits.SECOND)))
        assertEquals("через минуту", Date().humanizeDiff(Date().add(-1, TimeUnits.MINUTE)))
        assertEquals("через 29 дней", Date().humanizeDiff(Date().add(-29, TimeUnits.DAY)))
        assertEquals("только что", Date().humanizeDiff(Date().add(0, TimeUnits.HOUR)))
        assertEquals(
            "через несколько секунд",
            Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND))
        ) //несколько секунд назад
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff()) //2 часа назад
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff()) //5 дней назад
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff()) //через 2 минуты
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff()) //через 7 дней
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff()) //более года назад
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff()) //более чем через год
        assertEquals("только что", Date().humanizeDiff())
        assertEquals(
            "через несколько секунд",
            Date().humanizeDiff(Date().add(-2, TimeUnits.SECOND))
        ) //через несколько секунд
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("минуту назад", Date().add(-1, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("2 минуты назад", Date().add(-2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("3 минуты назад", Date().add(-3, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("5 минут назад", Date().add(-5, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("час назад", Date().add(-1, TimeUnits.HOUR).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("3 часа назад", Date().add(-3, TimeUnits.HOUR).humanizeDiff())
        assertEquals("4 часа назад", Date().add(-4, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 часов назад", Date().add(-5, TimeUnits.HOUR).humanizeDiff())
        assertEquals("день назад", Date().add(-1, TimeUnits.DAY).humanizeDiff())
        assertEquals("4 дня назад", Date().add(-4, TimeUnits.DAY).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("100 дней назад", Date().add(-100, TimeUnits.DAY).humanizeDiff())
    }

    @Test
    fun transliteration2() {
        assertEquals("[mi mi m]i", Utils.transliteration("[ми ми м]и"))
        assertEquals("Amazing_Petr", Utils.transliteration("Amazing Петр", "_"))

        assertEquals("Zh Zh", Utils.transliteration("Ж Ж"))
        assertEquals("ZhZh", Utils.transliteration("ЖЖ"))
        assertEquals("AbrAKadabra", Utils.transliteration("AbrAKadabra"))
        assertEquals("StraNNIi NikVash'e", Utils.transliteration("СтраННЫй НикВаще"))
    }
    @Test
    fun test_plural(){
        assertEquals("1 секунду", TimeUnits.SECOND.plural(1))
        assertEquals("2 секунды", TimeUnits.SECOND.plural(2))
        assertEquals("5 секунд", TimeUnits.SECOND.plural(5))
        assertEquals("11 секунд", TimeUnits.SECOND.plural(11))
        assertEquals("21 секунду", TimeUnits.SECOND.plural(21))
        assertEquals("22 секунды", TimeUnits.SECOND.plural(22))
        assertEquals("111 секунд", TimeUnits.SECOND.plural(111))

        assertEquals("1 минуту", TimeUnits.MINUTE.plural(1))
        assertEquals("2 минуты", TimeUnits.MINUTE.plural(2))
        assertEquals("5 минут", TimeUnits.MINUTE.plural(5))
        assertEquals("11 минут", TimeUnits.MINUTE.plural(11))
        assertEquals("21 минуту", TimeUnits.MINUTE.plural(21))
        assertEquals("22 минуты", TimeUnits.MINUTE.plural(22))

        assertEquals("1 час", TimeUnits.HOUR.plural(1))
        assertEquals("2 часа", TimeUnits.HOUR.plural(2))
        assertEquals("5 часов", TimeUnits.HOUR.plural(5))
        assertEquals("11 часов", TimeUnits.HOUR.plural(11))
        assertEquals("21 час", TimeUnits.HOUR.plural(21))
        assertEquals("22 часа", TimeUnits.HOUR.plural(22))

        assertEquals("1 день", TimeUnits.DAY.plural(1))
        assertEquals("2 дня", TimeUnits.DAY.plural(2))
        assertEquals("5 дней", TimeUnits.DAY.plural(5))
        assertEquals("11 дней", TimeUnits.DAY.plural(11))
        assertEquals("21 день", TimeUnits.DAY.plural(21))
        assertEquals("22 дня", TimeUnits.DAY.plural(22))
    }

    @Test
    fun test_truncate(){
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate())
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(14))
        println("A     ".truncate(3))
        println("A      hello world".truncate(3))
    }

    @Test
    fun test_stripHtml(){
        println("\"<p class=\"title\">Образовательное IT-сообщество Skill Branch</p>\"".stripHtml())
        println("\"<p>Образовательное       IT-сообщество Skill Branch</p>\"".stripHtml())
    }


    @Test
    fun stripHtmlTest() {
        /* skillBranch tests */
        assertEquals("Образовательное IT-сообщество Skill Branch",
            "<p class=\"title\">Образовательное IT-сообщество Skill Branch</p>".stripHtml())
        assertEquals("Образовательное IT-сообщество Skill Branch",
            "<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml())
        /* additional tests */
        assertEquals("single", "&amp;&lt;&gt;single&#39;&quot;".stripHtml())
        assertEquals("", "&amp;&lt;&gt;&#39;&quot;".stripHtml())
        assertEquals(" ", "&amp;&lt;&gt;    &#39;&quot;".stripHtml())
        assertEquals("1978", "<path fill=\"Color\" d=\"M11.63 10z\"></svg><span>1978</span>".stripHtml())
        assertEquals("", "&gt;<head>&#39;&quot;</head>".stripHtml())
        assertEquals(" ", "&gt;<head> &quot; </head>".stripHtml())
        assertEquals("&игра; amp lt &gt 39; meters ()quot;", "&игра; amp lt &gt 39; meters ()quot;".stripHtml())
        assertEquals(" one two ", "  one   two ".stripHtml())
        assertEquals("null", "null".stripHtml())
        val longHtml = """
            <TD valign="top" style="padding-bottom:15px;"> <b>line1<b> </TD>
            <TD valign="top"> <span class="HeadTitleNews"> line2</span>
            <img src='http://2011WaterpoloF.jpg' >
            <div style="margin: 0in 0in 0pt">line3</div>
        """.trimIndent()
        assertEquals(" line1 \n line2\n\nline3", longHtml.stripHtml())
    }

    @Test
    fun test_benderObj(){
        val benderObj = Bender()
        println(benderObj.listenAnswer("Bender"))
        println(benderObj.listenAnswer("Bender"))
        println(benderObj.listenAnswer("2716057"))
    }

    @Test
    fun test_keyboard_open(){

    }
}

//&amp;|&lt;|&gt;|&#39;|&quot; - это escape последовательности для html символов