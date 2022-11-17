package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOFile {

    private static IOFile instance = new IOFile();
    private BufferedReader arquivoDeEntrada;
    private BufferedWriter arquivoDeSaida;

    public IOFile() {
        this.arquivoDeEntrada = null;
        this.arquivoDeSaida = null;
    }

    public static IOFile getInstance() {
        return instance;
    }

    public void appendString(String nomeDoArquivo, String saida) throws IOException {
        this.arquivoDeSaida = new BufferedWriter(new FileWriter(nomeDoArquivo, true));
        this.arquivoDeSaida.write(saida);
        this.arquivoDeSaida.close();
    }

    public String getString(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        String linha = null;

        // Abrindo arquivo de entrada
        this.arquivoDeEntrada = new BufferedReader(new FileReader(file));
        while ((linha = this.arquivoDeEntrada.readLine()) != null && linha.compareTo("EOF") != 0) {
            result.append(linha).append("\n");
        }

        this.arquivoDeEntrada.close();

        return result.toString();
    }

    public String getString(String nomeDoArquivo) throws IOException {
        nomeDoArquivo = nomeDoArquivo.replaceAll("%20", " ");
        StringBuilder result = new StringBuilder();
        String linha = null;

        this.arquivoDeEntrada = new BufferedReader(new FileReader(nomeDoArquivo));
        while ((linha = this.arquivoDeEntrada.readLine()) != null && linha.compareTo("EOF") != 0) {
            result.append(linha);
            result.append("\n");
        }

        this.arquivoDeEntrada.close();

        return result.toString();
    }

    public void putString(String nomeDoArquivo, String saida) throws IOException {
        this.arquivoDeSaida = new BufferedWriter(new FileWriter(nomeDoArquivo));
        this.arquivoDeSaida.write(saida, 0, saida.length());
        this.arquivoDeSaida.close();
    }

    public void createFile(String filePath) throws IOException {
        File x = new File(filePath);
        x.createNewFile();
    }
}
