package com.example.weatherr.model.data

fun getRussianCities(): List<Weather> = listOf(
    Weather(City("Москва", 55.755826, 37.617299900000035), 1, 2),
    Weather(City("Санкт-Петербург", 59.9342802, 30.335098600000038), 3, 3),
    Weather(City("Новосибирск", 55.00835259999999, 82.93573270000002), 5, 6),
    Weather(City("Екатеринбург", 56.83892609999999, 60.60570250000001), 7, 8),
    Weather(City("Нижний Новгород", 56.2965039, 43.936059), 9, 10),
    Weather(City("Казань", 55.8304307, 49.06608060000008), 11, 12),
    Weather(City("Челябинск", 55.1644419, 61.4368432), 13, 14),
    Weather(City("Омск", 54.9884804, 73.32423610000001), 15, 16),
    Weather(City("Ростов-на-Дону", 47.2357137, 39.701505), 17, 18),
    Weather(City("Уфа", 54.7387621, 55.972055400000045), 19, 20)
)

