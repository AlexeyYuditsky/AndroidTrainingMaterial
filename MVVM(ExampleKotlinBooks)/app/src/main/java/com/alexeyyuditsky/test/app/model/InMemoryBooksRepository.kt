package com.alexeyyuditsky.test.app.model

import com.alexeyyuditsky.test.foundation.model.tasks.Task
import com.alexeyyuditsky.test.foundation.model.tasks.TasksFactory

class InMemoryBooksRepository(
    private val tasksFactory: TasksFactory
) : BooksRepository {

    private val listeners = mutableSetOf<BooksListener>()

    override fun getBooks(): Task<List<Book>> = tasksFactory.async {
        Thread.sleep(1000)
        return@async AVAILABLE_BOOKS
    }

    override fun getById(id: Long): Task<Book> = tasksFactory.async {
        Thread.sleep(1000)
        return@async AVAILABLE_BOOKS.first { it.id == id }
    }

    override fun changeBook(book: Book): Task<Unit> = tasksFactory.async {
        val bookIndex = AVAILABLE_BOOKS.indexOfFirst { it.id == book.id }
        AVAILABLE_BOOKS[bookIndex] = book
        listeners.forEach { it(AVAILABLE_BOOKS) }
    }

    override fun addListener(listener: BooksListener) {
        listeners += listener
    }

    override fun removeListener(listener: BooksListener) {
        listeners -= listener
    }

    companion object {
        private val AVAILABLE_BOOKS = mutableListOf(
            Book(
                id = 0,
                image = "https://media.proglib.io/posts/2021/08/15/9678e45f77f0fae9469a652d6afcc2c8.jpg",
                name = "«Head First. Kotlin»",
                authors = "Дон Гриффитс, Дэвид Гриффитс",
                year = "2019 год",
                description = "Эта книга научит быстро создавать приложения людей, ничего не знающих о программировании и не написавших ни одной строчки кода. Вам даже не нужно знать Java. Подача материала в ней, как и во всех книгах серии “Head first”, вовлечет ваш мозг в необходимый режим обучения, а не в сон. Почти каждая страница издания содержит изображения и диаграммы, помогающие объяснить основные концепции Kotlin. Четкое и лаконичное пособие с простым изложением сложных вещей"
            ),
            Book(
                id = 1,
                image = "https://media.proglib.io/posts/2021/08/15/43455e240f96fe6c7a6040887d879580.jpg",
                name = "«Kotlin. Программирование для профессионалов»",
                authors = "Джош Скин, Дэвид Гринхол",
                year = "2019 год",
                description = "Основанная на популярном курсе “Kotlin Essentials” от Big Nerd Ranch книга даст вам четкие объяснения ключевых концепций языка и не только познакомит вас с Kotlin, но и научит эффективно использовать его возможности, а также среду разработки IntelliJ IDEA от JetBrains. Несмотря на название издание подходит для начинающих и даст вам стартовый багаж фундаментальных знаний для создания надежных и эффективных приложений"
            ),
            Book(
                id = 2,
                image = "https://media.proglib.io/posts/2021/08/15/3da078eddb84ad0fca727142da12ff4e.png",
                name = "«Волшебство Kotlin»",
                authors = "Пьер-Ив Симон",
                year = "2020 год",
                description = "Это экспертное руководство опытного инженера поможет вам лучше понять основы и общие концепции языка для решения злободневных задач программирования. Много практических примеров, проработка часто возникающих ошибок и советы опытного программиста – вот то немногое, что есть в книге! Она научит вас писать понятные и простые в обслуживании, безопасные программы"
            ),
            Book(
                id = 3,
                image = "https://media.proglib.io/posts/2021/08/15/0a48313d66954c5644be0c16a03fd7f9.jpg",
                name = "«Kotlin в действии»",
                authors = "Дмитрий Жемеров, Светлана Исакова",
                year = "2017 год",
                description = "Книга научит вас использовать Kotlin для создания приложений, работающих на виртуальной машине Java на Android. Ее авторы книги работают в JetBrains с 2003 года и были одними из первых разработчиков, внесших огромный вклад в создание языка. Книга разделена на две части. В первой описывается, как начать использовать Kotlin вместе с существующими библиотеками и API, а вторая часть объясняет, как создать собственные API/абстракции и разобраться с нюансами разработки на Kotlin."
            ),
            Book(
                id = 4,
                image = "https://media.proglib.io/posts/2021/08/15/8e6e0aca56d4ece99d4ba9ab10e46337.jpg",
                name = "«Котлин. Программирование на примерах»",
                authors = "Ияну Аделекан",
                year = "2020 год",
                description = "Прочитав эту книгу, вы приобретете навыки разработки мобильных приложений при помощи мощных и интуитивно понятных инструментов и утилит Kotlin. Создадите три полноценных привлекательных программы с нуля (классическую игру Тетрис, мессенджер и веб-приложение, использующее API Карт Google) и научитесь их развертывать. Базовые знания программирования обязательны."
            ),
            Book(
                id = 5,
                image = "https://media.proglib.io/posts/2021/08/15/e42bd79784b5288d9f4a57a189ea318f.jpg",
                name = "«Effective Kotlin: Best practices»",
                authors = "Marcin Moskala",
                year = "2021 год",
                description = "В этой книге представлены и подробно описаны лучшие практики разработки на Kotlin. Каждый пункт представлен как четкое практическое правило, подкрепленное подробными объяснениями и примерами. Это исчерпывающее руководство по передовым методам обеспечения качества кода Kotlin: безопасности, удобочитаемости, дизайна и эффективности."
            ),
            Book(
                id = 6,
                image = "https://media.proglib.io/posts/2021/08/15/37df5775f609c022bab33e7e081dd141.png",
                name = "«Atomic Kotlin»",
                authors = "Bruce Eckel, Svetlana Isakova",
                year = "2021 год",
                description = "Книга даст вам фундаментальные навыки, необходимые для разработки на Kotlin. Изучаемый материал разбит на небольшие понятные главы (атомы) с упражнениями, подсказками и готовыми решениями прямо внутри IntelliJ IDEA."
            ),
            Book(
                id = 7,
                image = "https://media.proglib.io/posts/2021/08/15/0d8f797689aa5a64809bdac2cc07954f.jpg",
                name = "«Kotlin and Android Development featuring Jetpack: Build Better, Safer Android Apps»",
                authors = "Michael Fazio",
                year = "2021 год",
                description = "Благодаря Kotlin и Jetpack разработка под Android стала проще и приятнее чем когда-либо прежде. В этой книге вы с головой погрузитесь в програмирование, написав два полноценных приложения. Первое приложение (Penny Drop) – это игра в кости с настраиваемыми правилами и противниками с искусственным интеллектом. Второе приложение – Baseball League. В нем вы используете приобретенные при разработке первого приложения навыки и научитесь нескольким неплохим техникам."
            ),
            Book(
                id = 8,
                image = "https://media.proglib.io/posts/2021/08/15/220e62095410dc2eaf8551ee887a8da8.jpg",
                name = "«Beginning Android Development With Kotlin»",
                authors = "Michael Fazio",
                year = "2020 год",
                description = "Книга отправит вас в увлекательное практическое путешествие по изучению разработки приложений для Android с использованием Kotlin. Прочитав ее, вы сможете создать свое первое приложение с нуля за считанные минуты. Каждый раздел написан кратко и по существу. Автор поставил перед собой цель научить читателя Android-разработке, не перегружая теорией. Поэтому рассказывается в книге только о самом важном в практической манере, чтобы вы поскорее, смогли приступить к разработке."
            ),
            Book(
                id = 9,
                image = "https://media.proglib.io/posts/2021/08/15/9537d7050a913cf18b7696dd851e47a7.jpg",
                name = "«How to Build Android Apps with Kotlin»",
                authors = "Alex Forrester, Eran Boudjnah, Alexandru Dumbravan, Jomar Tigcal",
                year = "2021 год",
                description = "Это исчерпывающее руководство поможет вам начать разрабатывать программы под операционную систему Android. Через практические упражнения с подробными инструкциями вы узнаете, как создавать приложения и запускать их на виртуальных устройствах. Вы приобретете фундаментальные знания программирования и примените полученные навыки, используя лучшие отраслевые практики."
            ),
        )
    }

}