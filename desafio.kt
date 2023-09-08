enum class Nivel { BASICO, INTERMEDIARIO, AVANCADO, ESPECIALISTA }

class AlunosExcedidosException (message :String = "Número de alunos matriculados excedidos.") :Throwable (message)

class AlunoExistenteException (message :String = "Aluno com mesmo CPF já matriculado nesta formação.") :Throwable (message)

data class Usuario (var matricula :Long, var cpf :String, val nome :String, val senha :String) {
    override fun equals(other: Any?)
        = (other is Usuario)
        && cpf == other.cpf
}

data class ConteudoEducacional(val nome: String, val duracao: Int = 60)

data class Formacao(val nome: String, val nivel :Nivel, val conteudos: List<ConteudoEducacional>) {

    val inscritos = mutableMapOf<Long, Usuario>()
    
    fun matricular(usuario: Usuario) {
        
        log("Matriculando ${usuario.nome} na formação '$nome'...");
        log("Verificando...");
        
        try{
            if (inscritos.containsValue(usuario)) {
                throw AlunoExistenteException()
            }else if (inscritos.size <= 4) {
                while(inscritos.containsKey(usuario.matricula)){
                    usuario.matricula += 1;
                    println("+1 matr")
                }
                inscritos.put(usuario.matricula, usuario);
                log("Usuário matriculado com o número ${usuario.matricula}.", "sucesso");
            }else {
                throw AlunosExcedidosException();
            }
        }catch (e :Throwable){
            log(e.message.toString(), "erro");
        }finally{
            println("-------------------------------------------------------");
        }
        
        
        
    }
    
    fun cargaHoraria() = "A carga horária de '$nome' é de  ${conteudos.sumOf() {it.duracao}} minutos.";
    
	fun listarInscritos() :Unit {
        print("Inscritos na formação de $nome: ");
        inscritos.forEach { aluno ->
            print("${aluno.value.nome}, ");
        }
        println();
    }
    
}

fun log(mensagem :String, tag :String = "info"){
    println("[${tag.uppercase()}] - $mensagem");
}

fun main() {
    
    val javaFund = ConteudoEducacional("Fundamentos Java");
    val frameJava = ConteudoEducacional("Frameworks Java", 1000);
    val jvm = ConteudoEducacional("JVM", 10);
    val spring = ConteudoEducacional("Spring boot", 500);
    val js = ConteudoEducacional("js", 15);
    val poo = ConteudoEducacional("Programação Orientada a Objetos");
    val angular = ConteudoEducacional("Angular", 100);
    val fundKotlin = ConteudoEducacional("Fundamentos kotlin");
    val springKotlin = ConteudoEducacional("Spring com kotlin");
    val kotlinMp = ConteudoEducacional("Kotlin Multiplatform");
    val compose = ConteudoEducacional("Compose com kotlin");
    val node = ConteudoEducacional("Node");
    val nest = ConteudoEducacional("Nest.js");
    
    val especialistaJava = Formacao("Especialista java",Nivel.ESPECIALISTA, listOf(javaFund, frameJava, jvm, spring, poo, angular));
    val frontend = Formacao("Frontend",Nivel.INTERMEDIARIO , listOf(js, poo, angular, compose, node, nest));
    val kotlin = Formacao("Especialista kotlin",Nivel.AVANCADO, listOf(poo, fundKotlin, jvm, springKotlin, kotlinMp, compose));
    
    println("CARGA HORÁRIA DAS FORMAÇÕES");
    println("-------------------------------------------------------");
    println(especialistaJava.cargaHoraria());
    println(frontend.cargaHoraria());
    println(kotlin.cargaHoraria());
    println("-------------------------------------------------------");
    println("-------------------------------------------------------");
    
    val euMesmo = Usuario(1L, "01234567891", "Eduardo Abreu", "PinkFloydRule$");
    val bella = Usuario(1L, "00000000000", "Isabella", "12345");
    val meuAmigo = Usuario(4L, "11111111111", "Estevão", "qwerty");
    val meuOutroAmigo = Usuario(1L, "91234567891", "Lucas", "12345789");
    val desconhecidoUm = Usuario(1L, "12345678910", "Celso Portiolli", "123");
    val desconhecidoDois = Usuario(1L, "10987654321", "Hulk", "98765");
    
    
    println("MATRICULAR ESPECIALISTA JAVA");
    println("-------------------------------------------------------");
    especialistaJava.matricular(euMesmo);
    especialistaJava.matricular(bella);
    especialistaJava.matricular(meuAmigo);
    especialistaJava.matricular(euMesmo);
    especialistaJava.matricular(meuOutroAmigo);
    especialistaJava.matricular(desconhecidoUm);
    especialistaJava.matricular(desconhecidoDois);
    
    println("MATRICULAR KOTLIN");
    println("-------------------------------------------------------");
    kotlin.matricular(euMesmo);
    kotlin.matricular(bella);
    kotlin.matricular(meuAmigo);
    println("MATRICULAR FRONTEND");
    println("-------------------------------------------------------");
    frontend.matricular(euMesmo);
    frontend.matricular(meuOutroAmigo);
    frontend.matricular(desconhecidoUm);
    println("-------------------------------------------------------");
    println("-------------------------------------------------------");

    especialistaJava.listarInscritos();
    frontend.listarInscritos();
    kotlin.listarInscritos();
    
    println("-------------------------------------------------------");
    println("-------------------------------------------------------");
}
