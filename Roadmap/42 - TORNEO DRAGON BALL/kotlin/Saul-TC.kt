/*
Esta es una solucion simple.
*/

import kotlin.random.Random


class Fighter (val id:Int) {
    var health: Int = 100
    val name: String = setValue("el Nombre: ")
    val damage: Int = setValue("el Daño: ").toInt()
    val defense: Int = setValue("la Defensa: ").toInt()
    val velocity: Int = setValue("la Velocidad: ").toInt()

    fun setValue (txt: String): String {
        print("Ingrese $txt")
        return readln()
    }

    fun punchOponent (oponent: Fighter) {
        if (oponent.defense < damage) {
            // Fallar golpe
            if (Random.nextInt(1, 11) <= 2) {
                println("$name golpea a ${oponent.name} y falla.")
                return
            }
            // Dar golpe
            print("$name golpea a ${oponent.name} y lo deja de ${oponent.health} a ")

            oponent.health -= damage - oponent.defense
            println("${oponent.health}.")
            return
        }
        // Mitigar golpe con la defensa
        print("$name golpea a ${oponent.name} y lo deja de ${oponent.health} a ")
        oponent.health -= (damage*.1).toInt()
        println("${oponent.health}.")
        return
    }
}

// Main
fun main() {
    // Funcion anidada para crear los pares de luchadores.
    fun createFights (fightersList: List<Fighter>): MutableList<List<Fighter>> {
        val fighters = fightersList.toMutableList()
        // Crear pares de luchadores.
        val fights = mutableListOf<List<Fighter>>()
        while (fighters.isNotEmpty()) {
            // Agregar luchador aleatoreo al primer par.
            var index = Random.nextInt(0, fighters.size)
            val fight = mutableListOf(fighters[index])
            // Quitarlo de la lista de espera.
            fighters.removeAt(index)

            // Asignarle un contrincante aleatoreo.
            index = Random.nextInt(0, fighters.size)
            fight.add(fighters[index])
            // Quitarlo tambien de la lista.
            fighters.removeAt(index)

            fights.add(fight)
        }
        return fights
    }

    // Solicitar datos de los luchadores.
    print("Ingresa el número de luchadores: ")
    var numFighters = readln().toInt() // Numero de luchadores en total.
    while (numFighters % 2 != 0) {
        print("Solo se permite un número de luchadores par. Inenta otro número: ")
        numFighters = readln().toInt()
    }
    var fighters = mutableListOf<Fighter>() // Lista de tipo <Figther> con los luchadores.
    println("\nIngresar los valores de los luchadores.")
    for (i in 0..<numFighters) {
        println("\nIngresa los valores del luchador ${i+1}:")
        fighters.add(Fighter(i))
    }

    // Iniciar torneo.
    var numFights = 1
    while (fighters.size > 1) {
        val fights = createFights(fighters)
        fighters = mutableListOf<Fighter>()
        for ((i, fight) in fights.withIndex()) {
            var flag = true
            // Luchadores de la batlla.
            val fighter0 = fight[0]
            val fighter1 = fight[1]
            println("\n\nBatalla ${numFights++}: ${fighter0.name} contra ${fighter1.name}")

            // Primer turno.
            var turn = 0

            // Cambiar turno al más rápido, y mostrar mensaje de aviso.
            if (fighter1.velocity > fighter0.velocity) {
                turn = 1
                println("${fight[turn].name} iniciará la batalla por ser más rápido!\n")
            } else if (fight[1].velocity == fight[0].velocity) {
                turn = Random.nextInt(0, 2)
                println("${fight[turn].name} iniciará la batalla por tener suerte!\n")
            } else {
                println("${fight[turn].name} iniciará la batalla por ser más rápido!\n")
            }

            // Mostrar mensaje de aviso si un luchador tiene menos daño que defensa el oponente.
            if (fighter0.damage < fighter1.defense) {
                println("El luchador ${fighter0.name} no tiene suficiente daño para la defensa de ${fighter1.name}, asi que solo le hará el 10% del daño")
            } else if (fighter1.damage < fighter0.defense) {
                println("El luchador ${fighter1.name} no tiene suficiente daño para la defensa de ${fighter1.name}, asi que solo le hará el 10% del daño")
            }

            while (true) {
                // Verificar la vida de los luchadores.
                if (fighter0.health <= 0) {
                    println("Ganó ${fighter1.name}!")
                    fighters.add(fighter1)
                    break
                } else if (fighter1.health <= 0) {
                    println("Ganó ${fighter0.name}!")
                    fighters.add(fighter0)
                    break
                }

                // Golpear segun el turno.
                if (turn == 0) {
                    fighter0.punchOponent(fighter1)
                    turn = 1
                } else {
                    fighter1.punchOponent(fighter0)
                    turn = 0
                }
            }
        }
    }
    println("\n\n\n${fighters[0].name} ganó el torneo!")
}
