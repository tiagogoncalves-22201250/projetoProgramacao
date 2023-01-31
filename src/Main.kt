import java.io.File

//================================== Ficheiro ===========================================================
fun leFicheiro(numLines: Int, numColumns: Int): List<String>
{
    return File("${numLines}x${numColumns}.txt").readLines()
}

//========================================= 1ª Parte =================================================================
//=========================================== Main ===================================================================

fun main()
{
    do
    {
        var sairJogo = false
        var linha: Int = -1
        var coluna: Int = -1
        var data: String?
        var opcao: Int?
        var validaTerreno: Boolean
        var validacaoData: String? = null
        do
        {
            validaTerreno = true
            opcao = pedirInteiro()
            if (opcao == 1)
            {
                data = ""
                validacaoData = null
                linha = inputLinhasOuColunas("linhas")
                coluna = inputLinhasOuColunas("colunas")
                validaTerreno = validaTamanhoMapa(linha, coluna)
                if (linha == 10 && coluna == 10)
                {
                    do
                    {
                        data = pedirDataNascimento()
                        validacaoData = validaDataNascimento(data)
                    } while (validacaoData == "Data invalida")
                }
            }
        } while (!validaOpcao(opcao) || !validaTerreno || validacaoData == "Menor de idade nao pode jogar")
        println("")
        if (opcao == 1)
        {
            var coordenada: String
            var coordenadaPair: Pair<Int, Int>?
            val contadoresVerticais = leContadoresDoFicheiro(linha, coluna, true)
            val contadoresHorizontais = leContadoresDoFicheiro(linha, coluna, false)
            val terreno = leTerrenoDoFicheiro(linha, coluna)
            println(criaTerreno(terreno, contadoresVerticais, contadoresHorizontais))
            do
            {
                do
                {
                    coordenada = pedirCoordenadas()
                    coordenadaPair = processaCoordenadas(coordenada, linha, coluna)
                } while (coordenadaPair == null)
                if (!(coordenadaPair.first == -2003 && coordenadaPair.second == -1998))
                {
                    if (!colocaTenda(terreno, coordenadaPair))
                    {
                        textoCoordenadasInvalidas(true)
                    }
                    else
                    {
                        println("\n"+ criaTerreno(terreno, contadoresVerticais, contadoresHorizontais))
                    }
                }
                else
                {
                    sairJogo = true
                }
            } while (!terminouJogo(terreno, contadoresVerticais, contadoresHorizontais) && !sairJogo)
        }
    } while (opcao != 0 && !sairJogo)
}

//========================================= Menu - 100% ===============================================================
//Função Obrigatoria - Gere o output da parte do Menu
fun criaMenu() : String
{
    return "\nBem vindo ao jogo das tendas\n\n1 - Novo jogo\n0 - Sair\n"
}

//Função Opcional- Le a opção introduzida pelo utilizador.
fun pedirInteiro(): Int?
{
    println(criaMenu())
    return readlnOrNull()?.toIntOrNull()
}

//Função Opcional - Valida se o input do pedirInteiro é valida ou não.
fun validaOpcao(opcao: Int?) : Boolean
{
    if(opcao != null || opcao == 0 || opcao == 1)
    {
        return true
    }
    println("Opcao invalida")
    return false
}

//======================== Valida as linhas e colunas - 100% =======================================

//Função Opcional - Valida se a linha e a coluna esta correta
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

//Função Opcional - Texto do menor de idade
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

//=========================Cria a legenda Horizontal e o cria legenda vertical ==============================

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
            return "${numDaColuna}"
        } else {
            return " ${numDaColuna}"
        }
    } else {
        return "  "
    }
}
//===================================== 2ª Parte =============================================================
//===================================Contadores das Colunas e Linhas =================================

//Função Obrigatoria - Le no ficheiro os contadores das colunas
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

//Função Obrigatoria - Lê o contador das linhas do ficheiro
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

//Função Obrigatoria - Gere se quero ler as linhas ou colunas do ficheiro
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

//=================================Terreno do Ficheiro ================================================
//Função Obrigatoria - Lê as coordenadas das Arvores do ficheiro e processa o terreno
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

//=========================Coordenadas inseridas =====================================================

//Função Opcional - Valida se o tamanho das coordenadas é valido ou não(por causa da virgula)
fun validarTamanhoCoordenadas(coordenadasStr: String, numLines: Int, numColumns: Int): Boolean
{
    val listaSeparada = coordenadasStr.split(",")
    if(listaSeparada.size != 2)
    {
        return false
    }
    val coordLinha = listaSeparada[0].toInt()
    val coordColuna = converteLetraNumero(listaSeparada[1])
    if(coordLinha > numLines || coordLinha < 1)
    {
        return false
    }
    if(coordColuna >= numColumns || coordColuna < 0)
    {
        return false
    }
    return true
}

//Função Opcional - Texto do input das coordenadas
fun textoCoordenadas(): String
{
    return "Coordenadas da tenda? (ex: 1,B)"
}

/*Função Opcional - Texto se as coordenadas estiverem invalidas
Ou se não for possivel colocar tenda naquelas coordenadas
 */
fun textoCoordenadasInvalidas(imprime: Boolean): String
{
    if(imprime)
    {
        println("Tenda nao pode ser colocada nestas coordenadas")
    }
    return "Tenda nao pode ser colocada nestas coordenadas"
}

//Função Opcional - Pede ao utilizador as coordenadas
fun pedirCoordenadas(): String
{
    println(textoCoordenadas())
    return readln()
}

//Função Obrigatoria - Verifica se a coordenada esta de acordo com as normas do jogo
fun processaCoordenadas(coordenadasStr: String?, numLines: Int, numColumns: Int): Pair<Int, Int>?
{
    if (coordenadasStr.equals("sair"))
    {
        return Pair(-2003,-1998)
    }
    if (coordenadasStr != null && coordenadasStr.length > 2)
    {
        val listaSeparada = coordenadasStr.split(",")
        if(listaSeparada.size != 2)
        {
            textoCoordenadasInvalidas(true)
            return null
        }
        val coordLinha = listaSeparada[0].toInt()
        val coordColuna = converteLetraNumero(listaSeparada[1])
        if(coordLinha > numLines || coordLinha < 1)
        {
            textoCoordenadasInvalidas(true)
            return null
        }
        if(coordColuna >= numColumns || coordColuna < 0)
        {
            textoCoordenadasInvalidas(true)
            return null
        }
        return Pair(coordLinha-1,coordColuna)
    }
    textoCoordenadasInvalidas(true)
    return null
}

//Done
fun letrasValidas(): Array<String>
{
    return arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
}

//Função Opcional - Retorna o número correspondente a letra inserida
fun converteLetraNumero(letra: String): Int
{
    var numero: Int = -1
    for (i in 0 until letrasValidas().size)
    {
        if (letra == letrasValidas()[i])
        {
            numero = i
        }
    }
    return numero
}

//=========================================Validar Arvore Ajacente ======================
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
        if(linha < numLinha)
        {
            horizontal = verificarHorizontal(terreno,coords,"A")
            if(linha < numLinha-1)
            {
                baixo = terreno[linha + 1][coluna] == "A"
            }
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

//============================= Validar Tenda Adjacente =======================================
//Função de computação gráfica - Obra de arte :)
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
                    if (linha != coords.first || coluna != coords.second)
                    {
                        if (terreno[linha][coluna] == "T")
                        {
                            return true
                        }
                    }
                }
            }
        }
    }
    return false
}

//=============================== Tendas ================================

//Função Obrigatoria - Conta as tendas pela coluna(vertical)
fun contaTendasColuna(terreno: Array<Array<String?>>, coluna: Int): Int
{
    var contador = 0
    for(linha in terreno.indices)
    {
        if(terreno[linha][coluna].equals("T"))
        {
            contador++
        }
    }
    return contador
}

//Função Obrigatoria - Conta as tendas pela linha(horizontal)
fun contaTendasLinha(terreno: Array<Array<String?>>, linha: Int): Int
{
    var contador = 0
    for (coluna in 0 until terreno[0].size)
    {
        if (terreno[linha][coluna].equals("T"))
        {
            contador++
        }
    }
    return contador
}

//Função obrigatoria - Verifica se é possivel colocar tenda naquela coordenada
fun colocaTenda(terreno: Array<Array<String?>>, coords: Pair<Int,Int>): Boolean
{
    if(coords.first < 0 || coords.first > terreno.size - 1 ||
        coords.second < 0 || coords.second > terreno[0].size - 1 )
    {
        return false
    }
    if (terreno[coords.first][coords.second]== "A")
    {
        return false
    }
    if(!temArvoreAdjacente(terreno,coords))
    {
        return false
    }
    if(temTendaAdjacente(terreno,coords))
    {
        return false
    }
    if(terreno[coords.first][coords.second] == "T")
    {
        terreno[coords.first][coords.second] = null
    }
    else
    {
        terreno[coords.first][coords.second] = "T"
    }
    return true
}

//================================ Terminou o jogo==========================
fun terminouJogo(terreno: Array<Array<String?>>, contadoresVerticais: Array<Int?>,
                 contadoresHorizontais: Array<Int?>): Boolean
{
    val somaHorizontais : Array<Int?> = Array(contadoresHorizontais.size) {0}
    val somaVerticais : Array<Int?> = Array(contadoresVerticais.size) {0}
    for(linha in 0 until terreno.size)
    {
        somaHorizontais[linha] = contaTendasLinha(terreno,linha)
        if (somaHorizontais[linha] == 0)
        {
            somaHorizontais[linha] = null
        }
    }
    for(coluna in 0 until terreno[0].size)
    {
        somaVerticais[coluna] = contaTendasColuna(terreno,coluna)
        if (somaVerticais[coluna] == 0)
        {
            somaVerticais[coluna] = null
        }
    }
    if(somaVerticais contentEquals contadoresVerticais && somaHorizontais contentEquals contadoresHorizontais)
    {
        fimDoJogo()
        return true
    }
    return false
}

//Função Opcional - Texto do terminou o jogo
fun fimDoJogo()
{
    println("Parabens! Terminou o jogo!")
}

//==========================================Criar o terreno =====================================
/*Função Opcional - Converte o Array dos contadores verticais para a String
Objetivo: contadoresVerticais Array para o criaTerreno (legenda Vertical mais a esquerda)
 */
fun transformarArrayIntEmString(terreno: Array<Array<String?>>, contadoresVerticais: Array<Int?>?) : String
{
    val valores : Array<Int?>? = contadoresVerticais
    var texto = ""

    if (valores != null) {
        for(i in 0 until valores.size)
        {
            if(valores[i]== null)
            {
                texto += "    "
            } else {
                texto += "   ${valores[i]}"
            }
        }
    }
    return texto
}

//Função Opcional - Traduz o "A" no triângulo e o null em espaço vazio
fun tradutorTerreno(terreno: Array<Array<String?>>, numLines: Int, numColumns: Int): String?
{
    var texto = ""
    if(terreno[numLines][numColumns] == "A")
    {
        texto += "\u25B3"
    }
    if(terreno[numLines][numColumns] == "T")
    {
        texto += "T"
    }
    if(terreno[numLines][numColumns] == null)
    {
        texto += " "
    }
    return texto
}

/*Função Obrigatoria - Cria o terreno do jogo
Também é a função mais complexa ehehehe (além de dificil)
 */
fun criaTerreno(terreno: Array<Array<String?>>,
                contadoresVerticais: Array<Int?>?,
                contadoresHorizontais: Array<Int?>?,
                mostraLegendaHorizontal: Boolean = true,
                mostraLegendaVertical: Boolean = true): String {
    val numColunas = terreno[0].size
    val numLinhas = terreno.size
    var terrenoFinal = ""

    //Mostra os contadores Verticais(Topo do terreno)
    if (contadoresVerticais != null)
    {
        terrenoFinal += ("    ${transformarArrayIntEmString(terreno, contadoresVerticais)}\n")
    }

    //Mostra a legenda Horizontal(A | B | C...)
    if (mostraLegendaHorizontal)
    {
        terrenoFinal += ("     | ${criaLegendaHorizontal(numColunas)}\n")
    }

    for (i in 0 until numLinhas)
    {
        //Mostra os contadores das linhas(Lado do terreno)
        if (contadoresHorizontais != null)
        {
            terrenoFinal += "${criaLegendaContadoresHorizontal(arrayOf(contadoresHorizontais[i]))}"
        }
        else
        {
            terrenoFinal += " "
        }
        //Mostra os numeros da lateral esquerda
        if (mostraLegendaVertical)
        {
            terrenoFinal += " ${criaLegendaVertical(true, i + 1)}"
        }
        else
        {
            terrenoFinal += "   "
        }
        //Inicio do desenho do mapa em si
        for (j in 0 until numColunas)
        {
            terrenoFinal += " | ${tradutorTerreno(terreno, i, j)}"
            if (j == numColunas - 1 && i != numLinhas -1)
            {
                terrenoFinal += "\n"
            }
        }
    }
    return terrenoFinal
}

//Função Obrigatoria - Cria a legenda dos contadores Horizontais
fun criaLegendaContadoresHorizontal(contadoresVerticais: Array<Int?>): String
{
    var texto = ""
    for(i in contadoresVerticais.indices)
    {
        if(contadoresVerticais[i] == null)
        {
            texto += (" ")
        }
        else if(contadoresVerticais[i] == 0)
        {
            texto += (" ")
        }
        else
        {
            texto += "%${1}s".format(contadoresVerticais[i])
        }
        if (i != contadoresVerticais.size - 1)
        {
            texto += "   "
        }
    }
    return texto
}