
package org.example;

import java.util.Scanner;

public class auditando {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        int escolha = -1;

        // Controle do robô
        boolean paradaEmergencia = false;
        boolean sistemaAtivo = false;
        boolean cameraAtiva = false;
        boolean gravacao = false;

        String situacao = "Aguardando";
        String indicador = "Verde";
        String rede = "Conectada";
        String visao = "Desligada";

        // Dados da produção
        int quantidade = 0;
        int aprovadas = 0;
        int tempoAcumulado = 0;

        int[] cores = new int[3];

        // Histórico de acontecimentos
        String[] historico = new String[100];
        int contadorLogs = 0;

        do {

            System.out.println("\n===== CONTROLE DA CÉLULA ROBÓTICA =====");
            System.out.println("1 - Acionar emergência");
            System.out.println("2 - Consultar informações do robô");
            System.out.println("3 - Alterar funcionamento do programa");
            System.out.println("4 - Testar conexão");
            System.out.println("5 - Registrar produção");
            System.out.println("6 - Controle da câmera");
            System.out.println("7 - Consultar histórico");
            System.out.println("8 - Resultados da produção");
            System.out.println("9 - Analisar OEE");
            System.out.println("10 - Desativar emergência");
            System.out.println("0 - Encerrar");
            System.out.print("Digite uma opção: ");

            escolha = entrada.nextInt();

            switch (escolha) {

                case 1:

                    if (!paradaEmergencia) {
                        paradaEmergencia = true;
                        System.out.println("ATENÇÃO! Emergência ativada!");

                        if (contadorLogs < historico.length) {
                            historico[contadorLogs++] = "Emergência ativada";
                        }

                    } else {
                        System.out.println("A emergência já está ativa.");
                    }

                    break;

                case 2:

                    System.out.println("\n--- INFORMAÇÕES DO EQUIPAMENTO ---");
                    System.out.println("Situação: " + situacao);
                    System.out.println("Indicador: " + indicador);
                    System.out.println("Programa: " +
                            (sistemaAtivo ? "Ligado" : "Desligado"));
                    System.out.println("Rede: " + rede);
                    System.out.println("Câmera: " +
                            (cameraAtiva ? "Ligada" : "Desligada"));
                    System.out.println("Gravação: " +
                            (gravacao ? "Ativa" : "Inativa"));
                    System.out.println("Visão computacional: " + visao);

                    break;

                case 3:

                    sistemaAtivo = !sistemaAtivo;

                    if (sistemaAtivo) {
                        System.out.println("Sistema colocado em funcionamento.");
                    } else {
                        System.out.println("Sistema desativado.");
                    }

                    if (contadorLogs < historico.length) {
                        historico[contadorLogs++] =
                                sistemaAtivo ? "Programa iniciado" : "Programa desligado";
                    }

                    break;

                case 4:

                    System.out.print("Informe o tempo sem resposta (segundos): ");
                    int atraso = entrada.nextInt();

                    if (atraso < 0) {
                        System.out.println("Tempo inválido.");
                    } else if (atraso > 3) {
                        rede = "Desconectada";
                        System.out.println("Falha na comunicação!");

                        if (contadorLogs < historico.length) {
                            historico[contadorLogs++] =
                                    "Perda de comunicação: " + atraso + " segundos";
                        }

                    } else {
                        rede = "Conectada";
                        System.out.println("Comunicação normal.");
                    }

                    break;

                case 5:

                    if (paradaEmergencia) {
                        System.out.println("Produção bloqueada: emergência ativa.");

                    } else if (!sistemaAtivo) {
                        System.out.println("Ative o programa antes de produzir.");

                    } else if (!rede.equals("Conectada")) {
                        System.out.println("Produção bloqueada: comunicação indisponível.");

                    } else {

                        System.out.println("Selecione a cor:");
                        System.out.println("1 - Vermelha");
                        System.out.println("2 - Verde");
                        System.out.println("3 - Azul");

                        int corEscolhida = entrada.nextInt();

                        if (corEscolhida >= 1 && corEscolhida <= 3) {

                            System.out.print("Informe a duração do ciclo (segundos): ");
                            int duracao = entrada.nextInt();

                            System.out.print("Resultado da separação (1 = correta / 0 = incorreta): ");
                            int resultado = entrada.nextInt();

                            if (duracao <= 0 || (resultado != 0 && resultado != 1)) {
                                System.out.println("Dados informados inválidos.");

                            } else {

                                quantidade++;
                                tempoAcumulado += duracao;
                                cores[corEscolhida - 1]++;

                                if (resultado == 1) {
                                    aprovadas++;
                                }

                                System.out.println("Registro de produção realizado!");

                                if (contadorLogs < historico.length) {
                                    historico[contadorLogs++] =
                                            "Produção da peça número " + quantidade;
                                }
                            }

                        } else {
                            System.out.println("Opção de cor não reconhecida.");
                        }
                    }

                    break;

                case 6:

                    cameraAtiva = !cameraAtiva;
                    gravacao = cameraAtiva;

                    if (cameraAtiva) {
                        System.out.println("Câmera em funcionamento e gravação iniciada.");
                    } else {
                        System.out.println("Câmera desligada e gravação encerrada.");
                    }

                    if (contadorLogs < historico.length) {
                        historico[contadorLogs++] =
                                cameraAtiva ? "Câmera ativada" : "Câmera desativada";
                    }

                    break;

                case 7:

                    System.out.println("\n--- HISTÓRICO DE EVENTOS ---");

                    if (contadorLogs == 0) {
                        System.out.println("Ainda não existem registros.");

                    } else {
                        for (int i = 0; i < contadorLogs; i++) {
                            System.out.println((i + 1) + ". " + historico[i]);
                        }
                    }

                    break;

                case 8:

                    System.out.println("\n===== RELATÓRIO DE PRODUÇÃO =====");
                    System.out.println("Total produzido: " + quantidade);

                    if (quantidade > 0) {

                        double media = (double) tempoAcumulado / quantidade;
                        double aproveitamento = (double) aprovadas / quantidade * 100;

                        System.out.printf("Média dos ciclos: %.2f segundos%n", media);
                        System.out.printf("Peças aprovadas: %.2f%%%n", aproveitamento);

                    } else {
                        System.out.println("Não existem dados para apresentar.");
                    }

                    System.out.println("Peças vermelhas: " + cores[0]);
                    System.out.println("Peças verdes: " + cores[1]);
                    System.out.println("Peças azuis: " + cores[2]);

                    break;

                case 9:

                    if (quantidade == 0) {
                        System.out.println("Não é possível calcular o OEE sem produção.");

                    } else {

                        System.out.print("Tempo total planejado (minutos): ");
                        int tempoPlanejado = entrada.nextInt();

                        System.out.print("Tempo de parada (minutos): ");
                        int tempoParado = entrada.nextInt();

                        System.out.print("Ciclo ideal por peça (segundos): ");
                        int cicloIdeal = entrada.nextInt();

                        if (tempoPlanejado <= 0 ||
                                tempoParado < 0 ||
                                tempoParado > tempoPlanejado ||
                                cicloIdeal <= 0 ||
                                tempoAcumulado <= 0) {

                            System.out.println("Não foi possível realizar o cálculo.");

                        } else {

                            double disponibilidade =
                                    (double) (tempoPlanejado - tempoParado)
                                            / tempoPlanejado * 100;

                            double desempenho =
                                    (double) cicloIdeal * quantidade
                                            / tempoAcumulado * 100;

                            if (desempenho > 100) {
                                desempenho = 100;
                            }

                            double qualidade =
                                    (double) aprovadas / quantidade * 100;

                            double resultadoOEE =
                                    disponibilidade * desempenho * qualidade / 10000;

                            System.out.printf("Disponibilidade: %.2f%%%n", disponibilidade);
                            System.out.printf("Desempenho: %.2f%%%n", desempenho);
                            System.out.printf("Qualidade: %.2f%%%n", qualidade);
                            System.out.printf("OEE final: %.2f%%%n", resultadoOEE);
                        }
                    }

                    break;

                case 10:

                    if (paradaEmergencia) {
                        paradaEmergencia = false;
                        System.out.println("Emergência desativada. Sistema liberado.");

                        if (contadorLogs < historico.length) {
                            historico[contadorLogs++] = "Emergência desativada";
                        }

                    } else {
                        System.out.println("O sistema não está em emergência.");
                    }

                    break;

                case 0:

                    System.out.println("Encerrando o supervisório...");

                    break;

                default:

                    System.out.println("Comando inválido. Tente novamente.");

            }

            // Atualização das condições do robô
            if (paradaEmergencia) {
                situacao = "Parado por emergência";
                indicador = "Vermelho";

            } else if (sistemaAtivo) {
                situacao = "Em funcionamento";
                indicador = "Azul";

            } else {
                situacao = "Aguardando operação";
                indicador = "Verde";
            }

            // Atualização da visão computacional
            if (!cameraAtiva) {
                visao = "Câmera desconectada";

            } else if (sistemaAtivo && !paradaEmergencia) {
                visao = "Analisando imagens";

            } else {
                visao = "Em espera";
            }

        } while (escolha != 0);

        entrada.close();
    }
}
