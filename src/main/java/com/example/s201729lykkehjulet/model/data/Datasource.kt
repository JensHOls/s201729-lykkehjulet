package com.example.s201729lykkehjulet.model.data

import com.example.s201729lykkehjulet.model.bonusList

class Datasource {

    fun loadBonusDescriptions(): List<BonusDescription> {
        val list = mutableListOf<BonusDescription>()

        for (bonus in bonusList) {
            list.add(BonusDescription(bonus.name + ": " + bonus.message))
        }
        return list
    }
}
