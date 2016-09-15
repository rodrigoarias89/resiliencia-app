package ar.com.lapotoca.resiliencia.utils;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rarias on 7/26/16.
 */
public class LyricsHelper {

    public final static Map<String, String> mapLyrics = new HashMap<String, String>() {{
        put("quenoimportenada", "El clima está en guerra con el mundo actual.\n" +
                "Los niños mueren y al petróleo le rezas.\n" +
                "La extracción pasó a ser algo \"natural\".\n" +
                "Beber cianuro y a los pueblos Qom zarpar.\n" +
                "\n" +
                "En la escuela no hay cupo para progresar.\n" +
                "Los 21 millones son por comentar.\n" +
                "No hay negocio si invertís al Garraham.\n" +
                "La hipocresía en globos vende la ciudad.\n" +
                "\n" +
                "No me mires de perfil,\n" +
                "que en tu silencio\n" +
                "se esconde la usura de este gobierno infiel.\n" +
                "Tu miedo es su comodín,\n" +
                "que no importe nada.\n" +
                "Unámonos en un grito de rebelión.\n" +
                "\n" +
                "El cáncer de la \"progresión\" transgénica.\n" +
                "La pachamama llora y escupe del mar.\n" +
                "La explotación, el hambre, el paco, el poxiran,\n" +
                "son el efecto de la ambición del mal.\n" +
                "\n" +
                "No me mires de perfil\n" +
                "que en tu silencio\n" +
                "se esconde la usura de este sistema infiel.\n" +
                "Tu miedo es su comodín,\n" +
                "que no importe nada.\n" +
                "Unámonos en un grito de rebelión.\n");
        put("armandolio", "Eludió a la hormona maligna. \n" +
                "Heredó esa zurda de Dios. \n" +
                "Le agradezco, abuela, la siembra divina de toque, gamberra y sudor. \n" +
                "Vómito en la cancha al periodismo. \n" +
                "Pelé se arrodilla ante dos.\n" +
                "De Gandolfi a Boateng sin cadera. \n" +
                "El Che se sonríe; Hay revolución.\n" +
                "\n" +
                "Y vas a ver, lo que es amar, en ese encuentro inmaculado. \n" +
                "Dos hombres y la redonda besándose desesperados. \n" +
                "Deja jugar…\n" +
                "\n" +
                "Fue el romance más puro del mundo, \n" +
                "la pelota y el diez, Maradó. \n" +
                "A un pueblo sufrido regó de alegría y estafó al pirata ladrón. \n" +
                "Se plantó ante lo establecido. \n" +
                "¿Viste alguna vez algo mejor? \n" +
                "Le pelota nunca la manchaste dame otra zurda y que vuelva el Diego!\n" +
                "\n" +
                "Y vas a ver lo que es amar en ese encuentro inmaculado. \n" +
                "Dos hombres y la redonda besándose desesperados. \n" +
                "Y vos que estás ahí abombado, sin ganar nada y comparando, deja de hablar que están jugando.\n");
        put("riodejaneiro", "Ya agotó el tiempo de descuento,\n" +
                "hemos llegado al final\n" +
                "Vos me dijiste \"paremos, lo siento\"\n" +
                "yo me bebía en el bar.\n" +
                "\n" +
                "Las madrugadas se hicieron refugio,\n" +
                "la angustia mi amistad\n" +
                "Me hacía falta escuchar Calamaro\n" +
                "sobre tu falda en la cama.\n" +
                "\n" +
                "Cae la noche,\n" +
                "voy a salir a reinar la ciudad\n" +
                "a encontrar alguien\n" +
                "que resucite mis ganas de andar.\n" +
                "\n" +
                "Quedó su ropa en mi dormitorio,\n" +
                "Aún su espíritu está,\n" +
                "Le rezaría a todos los demonios\n" +
                "así al infierno se va.\n" +
                "\n" +
                "Te liberaste y dejaste en mi cuerpo\n" +
                "un nudo existencial,\n" +
                "qué me amarra, me acosa y me pega,\n" +
                "hasta dejarme knockout.\n");
        put("decaravana", "Descabellado, loco y solo me camino la ciudad\n" +
                "buscando gente piola que no quiera dormir nunca más.\n" +
                "Me cansé de sacar del medio, otra roja y llueve mal.\n" +
                "Se va la vida y no pienso estar de reparto por acá!\n" +
                "\n" +
                "Si ya no estas, hoy seré el mismo que fui ayer,\n" +
                "Si ya no estas, hoy puedo ser….\n" +
                "\n" +
                "Llega la banda, hay brindis, todo se alborota a un lindo mal.\n" +
                "Cuando juntamos almas siento que capaz puedo volar.\n" +
                "Maquillo un poco mis problemas, froto manos al pensar\n" +
                "¡qué rico es celebrar la nada cuando el viento tira atrás!\n" +
                "\n" +
                "Si ya no estas, hoy seré el mismo que fui ayer,\n" +
                "Si ya no estas, hoy puedo ser….\n" +
                "\n" +
                "Arriesgar, decidir y no callarme jamás \n" +
                "son mis lemas desde siempre \n" +
                "aunque cueste mucho más.\n");
        put("resilientes", "Vas masticando sueños, vas combatiendo infiernos \n" +
                "y no sabés por donde andas.\n" +
                "Los garrones existen, y hay que saber parirlos, para poder después remar. \n" +
                "\n" +
                "¿y qué me estás diciendo? ¿qué me baje de un sueño?\n" +
                "Mi raza lucha hasta el final. \n" +
                "Cobardes pecadores que anestesian deseos resignado su libertad. \n" +
                "\n" +
                "Vas caminando cielos, al dolor resistiendo y tu objetivo ahí está. \n" +
                "La frustración nos pega, esta es la vida misma y hay huevos para levantar.\n" +
                "\n" +
                "Si alguna vez me salí del sendero (qué es otra parte de mi ser)\n" +
                "Puedo fingir aún lo que te estoy diciendo, y eso me cueste morir.\n" +
                "\n" +
                "¿Qué mierda estás diciendo? ¿Qué me baje de un sueño?\n" +
                "Mi raza lucha hasta el final. \n" +
                "Cobardes pecadores que anestesian deseos resignado su libertad.\n");
        put("jardinesdeavellaneda", "Busco en las piedras la perfecta para vos.\n" +
                "La rabiosa, brava, la que sabe del valor.\n" +
                "Vuela alto y lejos como dientes de león,\n" +
                "pero llega al hueso, al hueso y corazón.\n" +
                "\n" +
                "Linda es la patada al hormiguero y a correr\n" +
                "de un tropel amigo que ha perdido a que temer;\n" +
                "greda, palos, ciegas moscas se echan a volar \n" +
                "y esa abeja reina que se muere por matar.\n" +
                "\n" +
                "No hay batallas vanas cuando lo veo sangrar,\n" +
                "esa herida duele y no la quiero ver cerrar.\n" +
                "Fábricas de fuego imposibles de apagar.\n" +
                "Hoy su grito es mío y no me importa nada más.\n" +
                "\n" +
                "Las libélulas de acero y de papel,\n" +
                "asechan perversas la tormenta con su hiel\n" +
                "y esta turba sigue devorando junto al sol\n" +
                "el testigo amigo del desgarro de mi voz.\n");
        put("campeonesdeesquina", "Chamuyaron que \"gloria\"\n" +
                "era un cheque en blanco,\n" +
                "que al \"campeón\" lo encontramos\n" +
                "en la tele, en los bancos.\n" +
                "\n" +
                "Mis campeones de esquina\n" +
                "no manejan un mango\n" +
                "y lo poco que tienen \n" +
                "se lo dan al de al lado.\n" +
                "\n" +
                "Cambian tu día oscuro\n" +
                "con su oído, algún trago\n" +
                "Al dolor lo doblegan\n" +
                "Ellos nacen del barro.\n" +
                "\n" +
                "No hay más letargos\n" +
                "(A los ojos te hablo)\n" +
                "No hay careta que seduzca\n" +
                "a un corazón cantando.\n" +
                "\n" +
                "Chamuyaron que \"gloria\"\n" +
                "es al buitre mamarle,\n" +
                "que al \"campeón\" lo encontramos\n" +
                "negociando y de traje.\n" +
                "\n" +
                "Mis campeones de esquina\n" +
                "le torean a la muerte, \n" +
                "callejean sus derechos\n" +
                "con memoria entre dientes.\n" +
                "\n" +
                "No hay más letargos\n" +
                "(A los ojos te hablo).\n" +
                "No hay careta que seduzca\n" +
                "a un corazón cantando.\n" +
                "\n" +
                "Si me quedo solo \n" +
                "por decir las cosas que voy sintiendo\n" +
                "yo a este mundo,\n" +
                "no le pertenezco.\n");
        put("aunconfieso", "Ya mis cuentos no encantaban, \n" +
                "pasaste a escribir vos.\n" +
                "Caminamos despacito buscando un…qué se yo.\n" +
                "Aún confieso que la noche se hace larga. \n" +
                "Hoy charló sólo e inventando que el vino es para dos... \n" +
                "\n" +
                "Y la Cruz la llevo porque estas cada mañana en mi vida. \n" +
                "Yo no soy el tipo que se fue y tampoco tu alegría. \n" +
                "\n" +
                "Y confieso que me cuesta contarte como soy. \n" +
                "El silencio y mis miedos clavaron mis manos. \n" +
                "No es barato estar de oferta cuando tú gran pasión\n" +
                " se ha vendido y se aleja en la feria del amor. \n" +
                "\n" +
                "Y la Cruz la llevo porque estas cada mañana en mi vida. \n" +
                "Yo no soy el tipo que se fue y tampoco tu alegría. \n" +
                "\n" +
                "Confieso que busco el reflejo de tu alma en el rostro de otra y tiendo a arruinar. \n" +
                "Y eso se da porque mi cuerpo es adicto a tu piel que perfuma a mi soledad.\n" +
                "Y estoy aquí repleto de bronca y con ganas de abrazarte hasta enloquecer.\n");
        put("beatlesvinoyreir", "Recordé cuando dijiste\n" +
                "“amate para amar”.\n" +
                "Sencillez, Beatles, vino y reír\n" +
                "tu forma de enseñar.\n" +
                "La bondad fue tan grande eras afano acá. \n" +
                "\n" +
                "Voy peleándole a la vida junto a vos, aunque ya no estás, \n" +
                "y me enfoco en repetir todo tu amor desde la verdad.\n" +
                "\n" +
                "Me decís que desde el sofá \n" +
                "todo se ve normal, \n" +
                "pero el brillo vive en arriesgar\n" +
                "una y mil veces más.\n" +
                "\n" +
                "Voy peleándole a la vida junto a vos, aunque ya no estás, \n" +
                "y me enfoco en repetir todo tu amor desde la verdad.\n" +
                "\n" +
                "Volando ahí estás, con vos el sol juega.\n" +
                "Volando ahí estás, con vos el sol juega.\n");
        put("certeza", "Que bien que estás\n" +
                "delineada en el sofá!\n" +
                "Bob Marley suena y vos cantas.\n" +
                "\n" +
                "Labios sedientos, \n" +
                "un vino tinto va a ayudar\n" +
                "y por tu cuerpo voy a entrar.\n" +
                "\n" +
                "Pero gritas sin consuelo\n" +
                "para exprimir la pasión.\n" +
                "Mañana no existen dueños,\n" +
                "la certeza es que esta noche yo manejo tu corazón.\n" +
                "\n" +
                "Los ojos rojos\n" +
                "y a mi oído susurras:\n" +
                "“Hoy no me pienso enamorar”\n" +
                "\n" +
                "Estamos perdidos,\n" +
                "pero me siento vivo acá,\n" +
                "y entre tus piernas levitar.\n" +
                "\n" +
                "Pero gritas sin consuelo\n" +
                "para exprimir la pasión.\n" +
                "Mañana no existen dueños,\n" +
                "la certeza es que esta noche yo manejo tu corazón.\n");


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
