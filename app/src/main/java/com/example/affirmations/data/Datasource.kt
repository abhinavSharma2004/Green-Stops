package com.example.affirmations.data

import com.example.affirmations.R
import com.example.affirmations.model.Affirmation

class Datasource() {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation("Mayapuri", 5.0,0),
            Affirmation("Naraina Vihar", 7.0,1),
            Affirmation("Delhi Cantt", 4.0,2),
            Affirmation("Durgabai Deshmukh", 5.0,3),
            Affirmation("Moti Bagh", 6.0,4),
            Affirmation("Bikaji Cama", 2.0,5),
            Affirmation("Sarojini Nagar", 8.0,6),
            Affirmation("Delhi Haat", 12.0,7),
            Affirmation("South Extension", 4.0,8),
            Affirmation("Lajpat Nagar", 1.0,9))
    }
}
