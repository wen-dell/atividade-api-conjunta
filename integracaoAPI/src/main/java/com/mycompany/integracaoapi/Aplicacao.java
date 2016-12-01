package com.mycompany.integracaoapi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class Aplicacao {

    public static String acessarYoda(String mensagem) {
        mensagem = Aplicacao.tratarString(mensagem);
        try {
            HttpResponse<String> response = Unirest.get("https://yoda.p.mashape.com/yoda?sentence=" + mensagem)
                    .header("X-Mashape-Key", "2oFoGI8iDWmshtDyWfMA6hn4P3AZp1tI60djsnNR2MRgAIqu35")
                    .header("Accept", "text/plain")
                    .asString();
            return response.getBody();
        } catch (UnirestException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return null;
    }

    public static String acessarIGN(String mensagem) {
        mensagem = tratarString(mensagem);
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get("https://videogamesrating.p.mashape.com/get.php?game=" + mensagem)
                    .header("X-Mashape-Key", "FC2RAZxpaDmshpSVSiZe0Yt6Rcugp1ehKItjsnhRhGAtBYzsMQ")
                    .header("Accept", "application/json")
                    .asJson();

            JSONArray games = response.getBody().getArray();
            
            StringBuilder string = new StringBuilder();
            
            for (int i = 0; i < games.length(); i++) {
                JSONObject game = games.getJSONObject(i);

                String title = game.getString("title");
                String publisher = game.getString("publisher");
                String score = game.getString("score");
                String description = game.getString("short_description");
                
                //description = acessarYoda(description);
                
                string.append((String.format("Nome: %s \n"
                        + "Distribuídora: %s \n"
                        + "Nota: %s \n"
                        + "Descrição curta: %s \n \n",
                        title, publisher, score, description)));
            }
            return string.toString();
        } catch (UnirestException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return null;
    }

    private static String tratarString(String mensagem) {
        mensagem = mensagem.replace(" ", "+");
        mensagem = mensagem.replace("\"", "");
        return mensagem;
    }

}
