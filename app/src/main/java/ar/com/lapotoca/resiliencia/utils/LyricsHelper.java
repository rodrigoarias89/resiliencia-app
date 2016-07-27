package ar.com.lapotoca.resiliencia.utils;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarias on 7/26/16.
 */
public class LyricsHelper {

    public final static Map<String, String> mapLyrics = new HashMap<String, String>() {{
        put("mayofrances", "Ni en mayo Frances me juego que hubo tanto fuego\n" +
                "(su silueta aun en mi cama).\n" +
                "Ni en mayo Francés me juego que hubo tanto fuego\n" +
                "(Su silueta aún en mi cama).\n" +
                "Suplicando lluvia que sacié mi cara tenue\n" +
                "o narcosis matinal.\n" +
                "\n" +
                "Con el alma ya vencida\n" +
                "y los ojos marchitados,\n" +
                "me reinvento en sueños.\n" +
                "\n" +
                "Comenzaste con finura a buscar mis besos\n" +
                "sin preguntas ni charlar.\n" +
                "Un estilo muy seguro de decir te quiero\n" +
                "del que no quiero escapar.\n" +
                "\n" +
                "Conquistaste mi vacío.\n" +
                "Vos creaste y derrumbaste\n" +
                "el guión perfecto.\n" +
                "\n" +
                "Ya no tengo un corazón que me pueda soportar\n" +
                "los momentos donde el frío abraza mi cuerpo.\n" +
                "El reloj marcó las tres\n" +
                "nada puede suceder,\n" +
                "salvo que tu boca sane todos mis lamentos.\n" +
                "\n" +
                "Del archivo no resisto ni de tu recuerdo.\n" +
                "Reencontré a mis lágrimas.\n" +
                "Cinco viajes , mil noches inmersos sin sosiego\n" +
                "a un oasis pasional.");
        put("catarsis", "Después de estancarme en un pozo ciego.\n" +
                "Después que me defrauden. Harto del fraudulento.\n" +
                "\n" +
                "Cambiaron mis ojos ya no creo en cuentos.\n" +
                "La Viveza Criolla nuestro peor ejemplo.\n" +
                "\n" +
                "Romper lazos con la egolatría y reír sin miedo.\n" +
                "A un pueblo ignorante lo domina el silencio.\n" +
                "\n" +
                "Yo no soy un pendejo.\n" +
                "Yo no escribo leyes.\n" +
                "¡A cantar sin filtros!\n" +
                "¡Es hora de despertar!\n" +
                "\n" +
                "La libertad es educar\n" +
                "con libros que caminan\n" +
                "El resignar es suicidar.\n" +
                "La tele estupidiza.\n" +
                "\n" +
                "Antes que nos liguen otra soga al cuello\n" +
                "que el poseer no te induzca, a ser su esclavo moderno\n" +
                "\n" +
                "Pensá por vos mismo y no repitas lo que no creas cierto.\n" +
                "Los consejos de un viejo no siempre abren los cielos\n" +
                "\n" +
                "Yo no soy un pendejo.\n" +
                "Yo no escribo leyes.\n" +
                "¡A cantar sin filtros!\n" +
                "¡Es hora de despertar!\n" +
                "\n" +
                "La libertad es educar\n" +
                "con libros que caminan.\n" +
                "El resignar es suicidar.\n" +
                "La tele estupidiza\n" +
                "\n" +
                "Y no me vengan con el verso del sistema\n" +
                "esto es América y nos sobra la miseria.\n" +
                "Qué paradoja que hay\n" +
                "Justicia, honestidad\n" +
                "Secuestradas están!");
        put("elconsumismonosconsume", "Medios complotados a un circo exquisito.\n" +
                "Tus tres celus y en Congo la muerte ahí va...\n" +
                "Ellos dicen fabricar el parche que va aliviar\n" +
                "el agujero existencial, el nuevo sol.\n" +
                "\n" +
                "Te gestionan a tu dios como el dinero\n" +
                "más imponen a quien hay que ir a rezar.\n" +
                "Vos creíste y comprás, los seguís sin patalear\n" +
                "¿No pensaste, por qué nunca te llenas?\n" +
                "\n" +
                "Ni en el éxtasis de medianoche\n" +
                "ni en la estúpida moda de hoy\n" +
                "vas encontrar jamás\n" +
                "la razón que llenará\n" +
                "como en el nacer de tu amor...no no no\n" +
                "\n" +
                "Revelate y sacudí tu pensamiento.\n" +
                "Esto agota y ni una gota va a quedar.\n" +
                "Su ambición de posesión\n" +
                "es gemelo al dolor y al vació\n" +
                "que maneja su facción.\n" +
                "\n" +
                "¡Ellos mandan porque hay alguien que obedece!\n" +
                "A tu oro no me lo puedo morfar.\n" +
                "¿Querés ser campeón mundial?\n" +
                "derroca el lucro mental\n" +
                "dale pan a la verdad, casa y canción.");
        put("dame", "Dame alguna alternativa,\n" +
                "dame alguna chance en V.\n" +
                "Dame un mundo de ilusiones aunque incite al daño.\n" +
                "Conjuguemos el placer.\n" +
                "\n" +
                "Dame tu soledad\n" +
                "¡dame tu libertad!\n" +
                "\n" +
                "Dame un manantial de sueños,\n" +
                "dame tu anhelo también,\n" +
                "Dame toda tu incoherencia aunque envenene al tiempo.\n" +
                "Redimí lo que seré.\n" +
                "Dame tu soledad\n" +
                "¡dame tu libertad!\n" +
                "\n" +
                "Dame la astucia cuando brillás.\n" +
                "Dame una lluvia de placer.\n" +
                "Dame tu sexo enajenado.\n" +
                "Dame un peldaño de poder.\n" +
                "\n" +
                "Son horas Tétricas en donde la nostalgia es Compañía,\n" +
                "el sol está nublado, dos almas se disipan .\n" +
                "\n" +
                "El imán de tu rechazo\n" +
                "cansa y enamora, ¿ves?\n" +
                "Un tire y afloje de pensar maniobras\n" +
                "para estar y no perder.\n" +
                "\n" +
                "Dame tu soledad\n" +
                "dame tu libertad!");
        put("lasmoscaseldineroyvos", "Explotando contra la almohada \n" +
                "no hay caricia que preste calma.\n" +
                "Gobernantes y policías\n" +
                "dan la mano y venden asfixia.\n" +
                "\n" +
                "Gente vana con un proyecto\n" +
                "Generar y agotar dinero.\n" +
                "Ni preguntes por la embestida\n" +
                "que el planeta planea algún día.\n" +
                "\n" +
                "Y no, no pienso en la infracción\n" +
                "de hacer ojo común\n" +
                "el ver pibes con hambre.\n" +
                "\n" +
                "Pero te tengo aquí\n" +
                "con tu hombro para un sí,\n" +
                "la magia es creer\n" +
                "que no veo sólo sed,\n" +
                "OK.\n" +
                "De a dos hay luz en casa.\n" +
                "\n" +
                "El tiempo no pasó\n" +
                "el mísero es el rey,\n" +
                "pingüinos y guasones\n" +
                "dueños de esta juerga\n" +
                "OK.\n" +
                "De a dos hay luz en casa.\n" +
                "\n" +
                "En un laberinto lleno de pretextos fríos\n" +
                "voy a celebrar con tu cuerpo y el mío.");
        put("idilio", "Pedir más sería palpar la ambición\n" +
                "(Siempre caigo en excesos).\n" +
                "Catarata impulsiva con sabor\n" +
                "de un célebre encuentro.\n" +
                "\n" +
                "Aún recuerdo mi asalto a tu armazón\n" +
                "sin rehenes y con versos.\n" +
                "Y esperando el arribo de tu no\n" +
                "del cielo cayó un beso.\n" +
                "\n" +
                "Probablemente crea que todo es la ilusión\n" +
                "de que tu piel es combo con la luna,\n" +
                "y sin embargo sienta que ya no hay más dolor\n" +
                "con tu presencia cerca de mis lunas.\n" +
                "\n" +
                "Amores clandestinos.\n" +
                "\n" +
                "Encontrar felicidad en este ruin\n" +
                "es utopía tonta,\n" +
                "pero un poco de esa sensación sentí\n" +
                "cuando bailaste loca.\n" +
                "\n" +
                "No creer en lo casual me hace pensar\n" +
                "y enroscarme en tu magia.\n" +
                "¡Una historia tan romántica de dos\n" +
                "que salva la distancia!");
        put("amediotomar", "Y así fue, se quebró nuestro dique\n" +
                "que sostenía ficción.\n" +
                "Dos guerreros maltratados\n" +
                "limando a la obsesión.\n" +
                "\n" +
                "El peor de los jueces metido\n" +
                "cumpliendo su gran misión.\n" +
                "Derretir los sentimientos,\n" +
                "lo apodan: El tiempo.\n" +
                "\n" +
                "Solo una condición es lo que pido:\n" +
                "No exijamos olvidar.\n" +
                "\n" +
                "Cada cual en su mesa y el tinto\n" +
                "que quede a medio tomar.\n" +
                "¡Qué loco que seas extraño!\n" +
                "En mis entrañas estás.\n" +
                "\n" +
                "Y un mensaje etílico a.m\n" +
                "buscando tu vanidad.\n" +
                "Un furgón de ansiedades\n" +
                "sin respuestas en vía.\n" +
                "\n" +
                "Solo una condición es lo que pido:\n" +
                "No exijamos olvidar.\n" +
                "Ese miedo a perderte\n" +
                "y a esa extraña soledad.\n" +
                "\n" +
                "Ay, amor querido no te vayas nunca,\n" +
                "quiero verte una vez más.");
        put("naufragio", "De olvidar me olvidé,\n" +
                "me hice fiel a mis lamentos.\n" +
                "Para anclar en la piel\n" +
                "mi rechazo a perdernos.\n" +
                "\n" +
                "Muchos consejos que son sanos\n" +
                "son insanos para mí,\n" +
                "voy a arruinarme en su sentir.\n" +
                "\n" +
                "Cuando el amor es compatible\n" +
                "imposible es compartir\n" +
                "mirar a la ilusión partir.\n" +
                "\n" +
                "Encontré, en su ser\n" +
                "un refugio a mi abismo.\n" +
                "Fue bordar mi babel,\n" +
                "ver el alma en equilibrio.\n" +
                "\n" +
                "Muchos consejos que son sanos\n" +
                "son insanos para mí,\n" +
                "voy a arruinarme en su sentir.\n" +
                "Cuando el amor es compatible\n" +
                "imposible es compartir\n" +
                "mirar a la ilusión partir.");
        put("losmarginados", "Dicen que somos los traidores\n" +
                "que deleitamos liberar.\n" +
                "El dolor nos transforma. Te voy a contar:\n" +
                "\n" +
                "Hijos, siervos de depresiones,\n" +
                "con la avidez de emancipar\n" +
                "a un mundo embrujado de frialdad.\n" +
                "\n" +
                "Hartos de amo, patrón y jefe\n" +
                "De descifrar sólo sobras.\n" +
                "(La inocencia ha golpeado feos uppercuts)\n" +
                "\n" +
                "Nacen secuelas desde un suelo\n" +
                "que alguna vez brotó: Esperar.\n" +
                "Secuencias que no busqué inhalar\n" +
                "\n" +
                "Los marginados estamos aquí\n" +
                "tan libres como toda honestidad,\n" +
                "la droga pide y la noche nos declara su amabilidad.\n" +
                "Los marginados estamos aquí\n" +
                "pidiendo pista, buscando intentar.\n" +
                "Lo gris es nuestro espejo\n" +
                "por vehemencia a quien no está más.\n" +
                "\n" +
                "Distinto es acusar de \"vagos\"\n" +
                "si no existe oportunidad.\n" +
                "Distinta es la cena\n" +
                "cuando ni hay pan\n" +
                "\n" +
                "En un cosmos de aflicciones\n" +
                "la chispa del crimen muestra\n" +
                "almas anhelando identidad.");


    }};

    public static String getLyrics(String songName) {
        String plainName;
        plainName = Normalizer.normalize(songName, Normalizer.Form.NFD);
        plainName = plainName.replaceAll("[^\\p{ASCII}]", "");
        plainName = plainName.replaceAll(" ", "");
        plainName = plainName.replaceAll(",", "");
        plainName = plainName.trim().toLowerCase();
        return mapLyrics.get(plainName);
    }

}
