import java.io.File

//================================== Ficheiro ===========================================================
fun leFicheiro(numLines: Int, numColumns: Int): List<String>
{
    return File("${numLines}x${numColumns}.txt").readLines()
}

//========================================= 1ª Parte =================================================================
//===========================================Main ==================================================================


//========================================= Menu - 100% ===============================================================
//Função Obrigatoria - Gere a parte do Menu.
fun criaMenu() : String
{
    return "\nBem vindo ao jogo das tendas\n\n1 - Novo jogo\n0 - Sair\n"
}

//Função Opcional- Le os Caracteres introduzidos pelo utilizador.
fun pedirInteiro(): Int?
{
    println(criaMenu())
    return readlnOrNull()?.toIntOrNull()
}

//Função Opcional - Valida se o input do pedirInteiro é valida ou não.
fun validaOpcao(opcao: Int?) : Boolean
{
    if(opcao == null || opcao < 0 || opcao > 1)
    {
        println("Opcao invalida")
        return false
    }
    return true
}

//======================== Valida as linhas e colunas - 100% =======================================

//Função Opcional - Valida se o input esta correto
fun validoNumero (numero: Int?): Boolean
{
    if (numero == null || numero < 0 || numero > 10)
    {
        println(msgRespostaInv())
        return false
    }
    return true
}

//Função Opcional - Retoma "resposta invalida
fun msgRespostaInv(): String
{
    return "Resposta invalida"
}

//Função Opcional - Gere a parte dos inputs da linha e da coluna
fun inputLinhasOuColunas(texto: String): Int
{
    var numero: Int?
    do
    {
        println("Quantas $texto?")
        numero = readln().toIntOrNull()
    } while (!validoNumero(numero))
    if(numero != null)
    {
        return numero
    }
    return 0
}

//===========Valida se o terreno é valido(ou seja, se as linhas e as colunas correspondem a algum tamanho) valido ======

//Função Opcional - Retoma "Terreno invalido"
fun terrenoInvalido()
{
    println("Terreno invalido")
}

//Função Obrigatoria - Gere se a linha e a coluna correspondem a uma configuração válida
fun validaTamanhoMapa(numLinhas: Int = 0, numColunas: Int = 0): Boolean
{
    var configuracao : Boolean
    if(numLinhas == 6 && numColunas == 5)
    {
        configuracao = true
    }
    else if(numLinhas == 6 && numColunas == 6)
    {
        configuracao = true
    }
    else if(numLinhas == 8 && numColunas == 8)
    {
        configuracao = true
    }
    else if(numLinhas == 8 && numColunas == 10)
    {
        configuracao = true
    }
    else if(numLinhas == 10 && numColunas == 8)
    {
        configuracao = true
    }
    else if(numLinhas == 10 && numColunas == 10)
    {
        configuracao = true
    }
    else
    {
        terrenoInvalido()
        configuracao = false
    }
    return configuracao
}

//===========================================Data de Nascimento - 100%=================================================
//Função Obrigatoria - Gere a data de nascimento
fun validaDataNascimento(data: String?): String?
{
    if(data != null && data.length == 10 && data[2] == '-' && data[5] == '-')
    {
        val separacao = data.split("-")
        if(separacao.size == 3)
        {
            val dia = separacao[0].toIntOrNull()
            val mes = separacao[1].toIntOrNull()
            val ano = separacao[2].toIntOrNull()
            if(ano != null && mes != null && dia != null)
            {
                if(validaAno(ano) && validaMes(mes) && validarDia(dia,mes,ano))
                {
                    if(menorIdade(mes,ano))
                    {
                        return textoMenorIdade(false)
                    }
                    return null
                }
            }
        }
    }
    return dataInvalida(true)
}

//Função Opcional - Gere o input da data
fun pedirDataNascimento(): String
{
    println("Qual a sua data de nascimento? (dd-mm-yyyy)")
    return readln()
}

//Função Opcional - Retoma "Data invalida"
fun dataInvalida(imprime: Boolean = false) : String
{
    val texto = "Data invalida"
    if(imprime)
    {
        println(texto)
    }
    return texto
}

//Função Opcional - Valida se o ano é valido
fun validaAno(ano: Int): Boolean
{
    if(ano < 1900 || ano > 2022)
    {
        return false
    }
    return true
}

fun textoMenorIdade(imprime: Boolean = false): String
{
    val texto = "Menor de idade nao pode jogar"
    if(imprime)
    {
        println(texto)
    }
    return texto
}

//Função Opcional - Valida se é menor de idade
fun menorIdade(mes: Int, ano: Int): Boolean
{
    if ((mes >= 11 && ano == 2004) || ano > 2004)
    {
        textoMenorIdade(true)
        return true
    }
    return false
}

//Função Opcional - Retoma o dia, baseado no mês e no ano
fun validarDia(dia: Int, mes:Int, ano: Int): Boolean
{
    if (mes == 4 || mes == 6 || mes == 9 || mes == 11)
    {
        if (dia > 0 && dia < 31)
        {
            return true
        }
        else
        {
            return false
        }
    }
    else if (mes == 2)
    {
        if ((ano % 4 == 0 && ano % 100 != 0) || ano % 400 == 0)
        {
            if (dia > 0 && dia <= 29)
            {
                return true
            } else {
                return false
            }
        } else {
            if (dia > 0 && dia <= 28) {
                return true
            } else {
                return false
            }
        }
    }
    else
    {
        if (dia > 0 && dia <= 31) {
            return true
        } else {
            return false
        }
    }
}

//Função Opcional - Valida se o mês é valido
fun validaMes(mes: Int) : Boolean
{
    if(mes < 0 || mes > 12)
    {
        return false
    }
    return true
}
//===================================Legenda Horizontal=================================================================

fun letrasLegenda():String
{
    return "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
}

//=========================Cria uma string que exibe as coordenadas das colunas do terreno==============================

fun criaLegendaHorizontal(numColunas: Int): String
{
    var legenda = ""
    var coluna = 0
    do {
        legenda += "${letrasLegenda()[coluna]}"
        coluna++
        if (coluna != numColunas)
        {
            legenda += " | "
        }
    } while (coluna < numColunas)
    return legenda
}

fun criaLegendaVertical(mostrarLegenda: Boolean = true, numDaColuna: Int): String
{
    if (mostrarLegenda){
        if (numDaColuna > 9) {
            return "${numDaColuna} |"
        } else {
            return " ${numDaColuna} |"
        }
    } else {
        return ""
    }
}

/*======================================Terreno total=============================================*/

fun criaLinhasTerreno(numColunas: Int): String
{
    var coluna = 0
    var legenda = ""
    while (coluna < numColunas)
    {
        if (coluna == numColunas - 1)
        {
            legenda += " \u25B3"
        }
        else
        {
            legenda += "   |"
        }
        coluna ++
    }
    return legenda
}

fun mostraLegendaVertical(tamanho: Boolean = true): Boolean
{
    if (tamanho)
    {
        return true
    }
    return false
}

fun mostraLegendaHorizontal(tamanho: Boolean = true): Boolean
{
    if (tamanho)
    {
        return true
    }
    return false
}

//===================================== 2ª Parte =============================================================

//Done
fun leContadoresColunas(numColumns: Int, ficheiro: List<String>) : Array<Int?>
{
    val valor : Array<Int?> = arrayOfNulls(numColumns)
    if(ficheiro.isEmpty())
    {
        return arrayOfNulls(numColumns)
    }
    else
    {
        val listaSeparada = ficheiro[0].split(",")
        for ((posicao, coluna) in listaSeparada.withIndex())
        {
            valor[posicao] = coluna.toIntOrNull()
            if(valor[posicao] == 0)
            {
                valor[posicao] = null
            }
        }
    }
    return valor
}

//Done
fun leContadoresLinhas(numLines: Int, ficheiro: List<String>) : Array<Int?>
{
    val valor : Array<Int?> = arrayOfNulls(numLines)
    if(ficheiro.isEmpty())
    {
        return arrayOfNulls(numLines)
    }
    else
    {
        val listaSeparada = ficheiro[1].split(",")
        for ((posicao, linha) in listaSeparada.withIndex())
        {
            valor[posicao] = linha.toIntOrNull()
            if(valor[posicao] == 0)
            {
                valor[posicao] = null
            }
        }
    }
    return valor
}

//Done
fun leContadoresDoFicheiro(numLines: Int, numColumns: Int, verticais: Boolean): Array<Int?>
{
    val ficheiro = leFicheiro(numLines,numColumns)
    return if(verticais)
    {
        leContadoresColunas(numColumns,ficheiro)
    }
    else
    {
        leContadoresLinhas(numLines,ficheiro)
    }
}

//Done
fun criaLegendaContadoresHorizontal(contadoresVerticais: Array<Int?>): String
{
    var texto = ""
    for(i in contadoresVerticais.indices)
    {
        if(contadoresVerticais[i] == null)
        {
            texto += ("    ")
        }
        else if(contadoresVerticais[i] == 0)
        {
            texto += ("    ")
        }
        else if(i == contadoresVerticais.size - 1)
        {
            texto += ("${contadoresVerticais[i]}")
        }
        else
        {
            texto += ("${contadoresVerticais[i]}   ")
        }
    }
    return texto
}

//Done
fun criaLegendaContadoresVertical(contadoresVertical: Array<Int?>): String
{
    var texto = ""
    for(i in contadoresVertical.indices)
    {
        if(contadoresVertical[i] == null)
        {
            texto += ("    ")
        }
        else if(contadoresVertical[i] == 0)
        {
            texto += ("    ")
        }
        else if(i == contadoresVertical.size - 1)
        {
            texto += ("${contadoresVertical[i]}")
        }
        else
        {
            texto += ("${contadoresVertical[i]}   ")
        }
    }
    return texto
}

// Done
fun leTerrenoDoFicheiro(numLines: Int, numColumns: Int): Array<Array<String?>>
{
    val ficheiro = leFicheiro(numLines,numColumns)
    val terreno: Array<Array<String?>> = Array(numLines) { Array(numColumns) { null }}
    for (linha in 2 until ficheiro.size)
    {
         for (coluna in 0 until numColumns)
         {
             val separacao = ficheiro[linha].split(",")
             val aLinha = separacao[0].toInt()
             val aColuna = separacao[1].toInt()
             terreno[aLinha][aColuna] = "A"
         }
    }
    return terreno
}

fun transformarArrayIntEmString(terreno: Array<Array<String?>>, verticais: Boolean) : String
{
    val valores : Array<Int?> = leContadoresDoFicheiro(terreno.size, terreno[0].size,verticais)
    var texto = ""
    for(i in 0 until valores.size)
    {
        texto += "   ${valores[i]}"
    }
    return texto
}

fun tradutorTerreno(terreno: Array<Array<String?>>, numLines: Int, numColumns: Int): String?
{
    var texto = ""
    if(terreno[numLines][numColumns] == "A")
    {
        texto += "\u25B3  |"
    }
    if(terreno[numLines][numColumns] == "T")
    {
        texto += "T |"
    }
    if(terreno[numLines][numColumns] == null)
    {
        texto += "   |"
    }
    return texto
}

fun criaTerreno(terreno: Array<Array<String?>>,
                contadoresVerticais: Array<Int?>?,
                contadoresHorizontais: Array<Int?>?,
                mostraLegendaHorizontal: Boolean = false,
                mostraLegendaVertical: Boolean = false): String
{
    val numColunas = terreno[0].size
    val numLinhas = terreno.size
    var terrenoFinal = ""

    if (contadoresHorizontais != null) {
        terrenoFinal += ("     ${transformarArrayIntEmString(terreno,true)}\n")
    }
    if (mostraLegendaHorizontal) {
        terrenoFinal += ("      | ${criaLegendaHorizontal(numColunas)}\n")
    }
    for (i in 0 until numLinhas - 1)
    {
        if (contadoresVerticais != null)
        {
            terrenoFinal += "  ${criaLegendaVertical(true, i + 1)}"
        }
        for (j in 0 until numColunas)
        {
            terrenoFinal += "  ${tradutorTerreno(terreno, i, j)}"
        }
        terrenoFinal += "\n"
    }
    return terrenoFinal
}

//Done
fun letrasValidas(): Array<String>
{
        return arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
    }

//Done
fun converteLetraNumero(coordenadasStr: String): Int
{
        val listaSeparada = coordenadasStr.split(",")
        val coluna = listaSeparada[1]
        var numero: Int = -1
        for (i in 0 until letrasValidas().size) {
            if (coluna == letrasValidas()[i]) {
                numero = i
            }
        }
        return numero
    }

//Done
fun textoCoordenadasInvalidas(): String
{
        return "Coordenadas invalidas"
}

//Done
fun validarTamanhoCoordenadas(coordenadasStr: String, numLines: Int, numColumns: Int): Boolean
{
        val listaSeparada = coordenadasStr.split(",")
        if (listaSeparada[0].toInt() <= numLines && converteLetraNumero(coordenadasStr) <= numColumns)
        {
            return true
        }
        return false
}

//Done
fun processaCoordenadas(coordenadasStr: String?, numLines: Int, numColumns: Int): Pair<Int, Int>?
{
        val coordenadas: Pair<Int, Int>?
        val listaSeparada = coordenadasStr?.split(",")
        if (coordenadasStr != null && coordenadasStr.length > 2)
        {
            if (validarTamanhoCoordenadas(coordenadasStr, numLines, numColumns))
            {
                val listaSeparada = coordenadasStr.split(",")
                return Pair(listaSeparada[0].toInt() - 1, converteLetraNumero(coordenadasStr))
            }
        }
        else
        {
            textoCoordenadasInvalidas()
            return null
        }
    return null
}

fun verificarHorizontal(terreno: Array<Array<String?>>, coords: Pair<Int, Int>, letra : String): Boolean
{
    val numColuna = terreno[0].size
    val linha = coords.first
    val coluna = coords.second
    var esquerdo = false
    var direito = false
    if(coluna > 0)
    {
        esquerdo = terreno[linha][coluna-1] == letra
        if(terreno[linha][numColuna-1] == letra)
        {
            esquerdo = terreno[linha][numColuna-1] == letra
        }
    }
    else if(coluna < numColuna)
    {
        direito = terreno[linha][coluna+1] == letra
    }
    if(esquerdo || direito)
    {
        return true
    }
    return false
}

fun verificarVertical(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean
{
    val numLinha = terreno.size
    val linha = coords.first
    val coluna = coords.second
    var cima = false
    var baixo = false
    var horizontal = false
    if(numLinha == 1)
    {
        horizontal = verificarHorizontal(terreno,coords,"A")
    }
    else if(numLinha > 1)
    {
        if(linha > 0)
        {
            horizontal = verificarHorizontal(terreno,coords,"A")
            cima = terreno[linha - 1][coluna] == "A"
        }
        else if(linha < numLinha)
        {
            horizontal = verificarHorizontal(terreno,coords,"A")
            baixo = terreno[linha + 1][coluna] == "A"
        }
    }
    if(cima || baixo || horizontal)
    {
        return true
    }
    return false
}

fun temArvoreAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean
{
    if(verificarVertical(terreno,coords))
    {
        return true
    }
    return false
}

fun temTendaAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean
{
    for (linha in coords.first - 1 until coords.first + 2)
    {
        if (linha >= 0 && linha < terreno.size)
        {
            for (coluna in coords.second - 1 until coords.second + 2)
            {
                if (coluna >= 0 && coluna < terreno[0].size)
                {
                    if (terreno[linha][coluna] == "T")
                    {
                        return true
                    }
                }
            }
        }
    }
    return false
}

fun contaTendasColuna(terreno: Array<Array<String?>>, coluna: Int): Int
{
    for(linha in terreno.indices)
    {
        for(colunas in 0 until terreno[0].size)
        {
            if(terreno[linha][coluna] == "T")
            {
                return 1
            }
        }
    }
    return 0
}

fun contaTendasLinha(terreno: Array<Array<String?>>, linha: Int): Int
{
    var contador = 0
    for (coluna in 0 until terreno[0].size) {
        if (terreno[linha][coluna].equals("T")) {
            contador++
        }
    }
    return contador
}

fun colocaTenda(terreno: Array<Array<String?>>, coords: Pair<Int,Int>): Boolean
{
    if(coords.first < 0 || coords.first > terreno.size - 1 ||
        coords.second < 0 || coords.second > terreno[0].size - 1 )
    {
        return false
    }
    else if(!temArvoreAdjacente(terreno,coords))
    {
        return false
    }
        terreno[coords.first][coords.second] = "T"
        return true
}

fun main() {
    var linha: Int = -1
    var coluna: Int = -1
    var data: String?
    var opcao: Int?
    var validaTerreno: Boolean
    var validacaoData: String? = null
    do {
        validaTerreno = true
        opcao = pedirInteiro()
        if (opcao == 1) {
            data = ""
            validacaoData = null
            linha = inputLinhasOuColunas("linhas")
            coluna = inputLinhasOuColunas("colunas")
            validaTerreno = validaTamanhoMapa(linha, coluna)
            if (linha == 10 && coluna == 10) {
                do {
                    data = pedirDataNascimento()
                    validacaoData = validaDataNascimento(data)
                } while (validacaoData == "Data invalida")
            }
        }
    } while (!validaOpcao(opcao) || !validaTerreno || validacaoData == "Menor de idade nao pode jogar")
}
    /*val contadoresVerticais = leContadoresDoFicheiro(linha, coluna, true)
    val contadoresHorizontais = leContadoresDoFicheiro(linha, coluna, false)
    val terreno = leTerrenoDoFicheiro(linha, coluna)
    print(criaTerreno(terreno,contadoresVerticais, contadoresHorizontais,true, true))*/

fun terminouJogo(
    terreno: Array<Array<String?>>, contadoresVerticais: Array<Int?>,
    contadoresHorizontais: Array<Int?>): Boolean
{
    val contadorL = 0
    var contadorC = 0
    val numLinhas = terreno.size
    var incremente = 0
    var validacaoVertical  = false
    var validacaoHorizontal = false
    for(linha in 0 until numLinhas)
    {
        if(incremente < terreno[0].size)
        {
            if(terreno[linha][incremente++] == "A")
            {
                contadorC++
            }
        }
        if(contadorC == contadoresHorizontais[linha])
        {
            validacaoHorizontal = true
        }
    }
    /*for(coluna in 0 until terreno[0].size)
    {
        print(terreno[linha][coluna]+ "   ")
        if (terreno[linha][coluna] == "A")
        {
            contadorL++
        }
    }*/
    for(i in 0 until contadoresVerticais.size)
    {
        if(contadorL == contadoresVerticais[i])
        {
            validacaoVertical = true
        }
    }
    for(i in 0 until contadoresHorizontais.size)
    {

    }
    println("Contador coluna: $contadorC")
    println("Contador linha: $contadorL")
    if(validacaoVertical && validacaoHorizontal)
    {
        return true
    }
    return false
}