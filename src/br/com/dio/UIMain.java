package br.com.dio;

import br.com.dio.ui.custom.screen.MainScreen;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {
        // --- INÍCIO DA CORREÇÃO ---
        try {
            System.out.println("Aguardando o ambiente gráfico iniciar (pausa de 3 segundos)...");
            Thread.sleep(3000); // Pausa por 3000 milissegundos (3 segundos)
            System.out.println("Ambiente pronto. Iniciando a UI do Sudoku.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // --- FIM DA CORREÇÃO ---

        final var gameConfig = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
        var mainsScreen = new MainScreen(gameConfig);
        mainsScreen.buildMainScreen();
    }

}