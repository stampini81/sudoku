package br.com.dio;

import br.com.dio.ui.custom.screen.MainScreen;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {
        // --- INÍCIO DA ALTERAÇÃO ---
        try {
            System.out.println("Aguardando o ambiente gráfico iniciar (pausa de 2 segundos)...");
            Thread.sleep(2000); // Pausa por 2000 milissegundos (2 segundos)
            System.out.println("Ambiente pronto. Iniciando a UI do Sudoku.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // --- FIM DA ALTERAÇÃO ---

        final var gameConfig = Stream.of(args)
                .collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
        var mainsScreen = new MainScreen(gameConfig);
        mainsScreen.buildMainScreen();
    }

}