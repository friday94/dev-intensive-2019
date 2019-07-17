package ru.skillbranch.devintensive.models

import java.util.regex.Pattern

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val messageAnswer: String
        if (question.validQuestion(answer) == "") {
            if ((question.answer.contains(answer.toLowerCase())) || (question==Question.IDLE) && (answer == "")) {
                question = question.nextQuestion()
                messageAnswer = "Отлично - ты справился\n${question.question}"
            } else {
                if (status.nextStatus() != Status.NORMAL) {
                    status = status.nextStatus()
                    messageAnswer = "Это не правильный ответ!\n${question.question}"
                } else {
                    status = status.nextStatus()
                    question = Question.NAME
                    messageAnswer = "Это не правильный ответ. Давай все по новой\n${question.question}"
                }
            }
        } else messageAnswer = question.validQuestion(answer) + "\n${question.question}"
        return messageAnswer to status.color
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }


    enum class Question(val question: String, val answer: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = NAME
        };

        fun validQuestion(answer: String): String {
            val goodMessage = ""
            return when (this) {
                NAME -> if (Pattern.matches(
                        "[А-ЯЁA-Z].*",
                        answer
                    )
                ) goodMessage else "Имя должно начинаться с заглавной буквы"
                PROFESSION -> if (Pattern.matches(
                        "[а-яa-z].*",
                        answer
                    )
                ) goodMessage else "Профессия должна начинаться со строчной буквы"
                MATERIAL -> if (Pattern.matches(
                        "[А-ЯЁA-Zа-яa-z]*",
                        answer
                    )
                ) goodMessage else "Материал не должен содержать цифр"
                BDAY -> if (Pattern.matches(
                        "[0-9]*",
                        answer
                    )
                ) goodMessage else "Год моего рождения должен содержать только цифры"
                SERIAL -> if (Pattern.matches(
                        "[0-9]{7}",
                        answer
                    )
                ) goodMessage else "Серийный номер содержит только цифры, и их 7"
                IDLE -> goodMessage
            }
        }

        abstract fun nextQuestion(): Question
    }


}