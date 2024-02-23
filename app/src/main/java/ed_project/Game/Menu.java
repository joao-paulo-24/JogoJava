package ed_project.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.parser.ParseException;

import ed_project.Enumerations.Teams;
import ed_project.Exceptions.*;
import ed_project.GameManagement.Play;

/**
 * @author 8210455 Bruno Rodrigues
 * @author 8210458 João Carvalho
 */
public class Menu {

    /**
     * Metodo construtor para a classe do menu
     */
    public Menu() {
    }

    /**
     * Main menu onde o jogador vai ter acesso a toda a api to programa
     * @throws IOException excessao de input/output
     * @throws NoSuchElementException excessao de elemento nao existir
     * @throws EmptyCollectionException excessao de lista estar vazia
     * @throws NoSuchTeamExist excessao de nao existir a equipa
     * @throws NotEnoughObjects excessao de nao serem dados objetos suficientes
     * @throws InvalidRoute excessao de rotas invalidas
     * @throws NumberFormatException excessao de formato de numero
     * @throws NotAcceptedValue excessao de valor nao aceite
     * @throws ParseException excessao de JSON parse
     */
    public void mainMenu() throws IOException, NoSuchElementException, EmptyCollectionException, NoSuchTeamExist, NotEnoughObjects, InvalidRoute, NumberFormatException, NotAcceptedValue, ParseException {
        Play p = new Play();
        

        int opcao1;
        do {
            System.out.println("          - Bem vindo -");
            System.out.println("\n0. Sair");
            System.out.println("1. Jogar");
            System.out.println("2. Painel de administrador");
            do {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                opcao1 = Integer.parseInt(br.readLine());
                if (opcao1 < 0 || opcao1 > 2) {
                    System.out.println("\n     --- Opcao invalida ---\n");
                }
            } while (opcao1 < 0 && opcao1 > 2);

            switch (opcao1) {
                case 1:
                    if (p.getGj().getPlayers().getCount() > 0 && p.getGl().getLocais().getCount() > 0) {
                        System.out.println("\nInsira o id da personagem com que quer jogar");
                        p.listarJogadores();
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        int idJogador = Integer.parseInt(br.readLine());
                        p.startGame(idJogador);
                        p.gamingMenu();
                    } else if (p.getGj().getPlayers().getCount() == 0 && p.getGl().getLocais().getCount() == 0) {
                        System.out.println("\nAinda não foi criado qualquer jogador ou local.\n");
                    } else if (p.getGl().getLocais().getCount() == 0) {
                        System.out.println("\nAinda não foi criado nenhum local.\n");
                    } else if (p.getGj().getPlayers().getCount() == 0){
                        System.out.println("\nAinda não foi criado nenhum jogador.\n");
                    }
                    break;
                case 2:
                    int opcao2;
                    do {
                        System.out.println("\n0. Voltar atras");
                        System.out.println("1. Gestão de jogadores");
                        System.out.println("2. Gestão de portais");
                        System.out.println("3. Gestão de conectores");
                        System.out.println("4. Gestão de rotas");
                        System.out.println("5. Gestão do jogo");
                        do {
                            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                            opcao2 = Integer.parseInt(br2.readLine());
                            if (opcao2 < 0 || opcao2 > 5) {
                                System.out.println("\n     --- Opcao invalida ---\n");
                            }
                        } while (opcao2 < 0 && opcao2 > 5);

                        switch (opcao2) {
                            case 1:
                                int opcao3;
                                do {
                                    System.out.println("\n0. Voltar atras");
                                    System.out.println("1. Adicionar jogador");
                                    System.out.println("2. Remover jogador");
                                    System.out.println("3. Atualizar jogador");
                                    System.out.println("4. Listar jogadores");
                                    System.out.println("5. Exportar ficheiro com dados dos jogadores");
                                    System.out.println("6. Importar ficheiro com dados de jogadores");
                                    do {
                                        BufferedReader br20 = new BufferedReader(new InputStreamReader(System.in));
                                        opcao3 = Integer.parseInt(br20.readLine());
                                        if (opcao3 < 0 || opcao3 > 6) {
                                            System.out.println("\n     --- Opcao invalida ---\n");
                                        }
                                    } while (opcao3 < 0 && opcao3 > 6);

                                    switch (opcao3) {
                                        case 1:
                                            int opcao4;
                                            System.out.println("\nInsire o nome do novo jogador:  ");
                                            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                                            String str = br2.readLine();
                                            System.out.println("\nEscolha agora a equipa que quer a que o jogador pertença");
                                            System.out.println("1. Sparks");
                                            System.out.println("2. Giants");
                                            do {
                                                BufferedReader br19 = new BufferedReader(new InputStreamReader(System.in));
                                                opcao4 = Integer.parseInt(br19.readLine());
                                                if (opcao4 < 1 || opcao4 > 2) {
                                                    System.out.println("\n     --- Opcao invalida ---\n");
                                                }
                                            } while (opcao4 < 0 && opcao4 > 2);

                                            switch (opcao4) {
                                                case 1:
                                                    p.addPlayer(str, opcao4);    
                                                    break;
                                                case 2:
                                                    p.addPlayer(str, opcao4);
                                                    break;
                                            }
                                            break;
                                        case 2:
                                            System.out.println("\nInsire o id do jogador que deseja remover:  ");
                                            BufferedReader br18 = new BufferedReader(new InputStreamReader(System.in));
                                            int id = Integer.parseInt(br18.readLine());
                                            p.removePlayer(id);
                                            break;
                                        case 3:
                                            int opcao5;
                                            do {
                                                System.out.println("\n0. Voltar atras");
                                                System.out.println("1. Atualizar nome");
                                                System.out.println("2. Mudar a equipa");
                                                do {
                                                    BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                                    opcao5 = Integer.parseInt(br3.readLine());
                                                    if (opcao5 < 0 || opcao5 > 2) {
                                                        System.out.println("\n     --- Opcao invalida ---\n");
                                                    }
                                                } while (opcao5 < 0 && opcao5 > 2);

                                                switch (opcao5) {
                                                    case 1:
                                                        System.out.println("\nInsire o id do jogador que deseja atualizar:  ");
                                                        BufferedReader br4 = new BufferedReader(new InputStreamReader(System.in));
                                                        int id2 = Integer.parseInt(br4.readLine());
                                                        System.out.println("\nInsire o novo nome do jogador:  ");
                                                        BufferedReader br5 = new BufferedReader(new InputStreamReader(System.in));
                                                        String str2 = br5.readLine();
                                                        p.updatePlayer(id2, str2);
                                                        break;
                                                    case 2:
                                                        System.out.println("Insire o id do jogador que deseja mudar de equipa:  \n");
                                                        BufferedReader br6 = new BufferedReader(new InputStreamReader(System.in));
                                                        int id3 = Integer.parseInt(br6.readLine());
                                                        p.switchPlayerTeams(id3);
                                                        break;
                                                }
                                            } while (opcao5 != 0);
                                            break;
                                        case 4:
                                            if (p.getGj().getPlayers().getCount() > 0) {
                                                int opcao6;
                                                do {
                                                    System.out.println("\n0. Voltar atras");
                                                    System.out.println("1. Listar por equipa");
                                                    System.out.println("2. Listar por nivel");
                                                    System.out.println("3. Listar por numero de portais atualmente conquistados");
                                                    do {
                                                        BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                                        opcao6 = Integer.parseInt(br3.readLine());
                                                        if (opcao6 < 0 || opcao6 > 3) {
                                                            System.out.println("\n     --- Opcao invalida ---\n");
                                                        }
                                                    } while (opcao6 < 0 && opcao6 > 3);

                                                    switch (opcao6) {
                                                        case 1:
                                                            p.listarPlayersPorTeam();
                                                            break;
                                                        case 2:
                                                            p.listarPlayerPorNivel();
                                                            break;
                                                        case 3:
                                                            p.listarPlayerPorPortaisConquistados();
                                                            break;
                                                    }
                                                } while (opcao6 != 0);
                                            } else {
                                                System.out.println("\nNão existem jogadores a serem listados");
                                            }
                                            break;
                                        case 5:
                                            p.exportToJSONPlayers();
                                            break;
                                        case 6:
                                            System.out.println("\nInsire o nome do ficheiro que quer importar:  ");
                                            BufferedReader br5 = new BufferedReader(new InputStreamReader(System.in));
                                            String str3 = br5.readLine();
                                            str3 += ".json";
                                            p.importPlayersFromJSON(str3);
                                            break;
                                    }
                                } while (opcao3 != 0);
                                break;
                            case 2:
                                int opcao7;
                                do {
                                    System.out.println("\n0. Voltar atras");
                                    System.out.println("1. Adicionar portal");
                                    System.out.println("2. Remover portal");
                                    System.out.println("3. Editar portal");
                                    System.out.println("4. Listar portais");
                                    do {
                                        BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                        opcao7 = Integer.parseInt(br3.readLine());
                                        if (opcao7 < 0 || opcao7 > 4) {
                                            System.out.println("\n     --- Opcao invalida ---\n");
                                        }
                                    } while (opcao7 < 0 && opcao7 > 4);

                                    switch (opcao7) {
                                        case 1:
                                            double lat, lon, energiaMax;
                                            do {
                                                System.out.println("Indique a latitude do portal:  ");
                                                BufferedReader br7 = new BufferedReader(new InputStreamReader(System.in));
                                                lat = Double.parseDouble(br7.readLine());
                                                if (lat < -90 || lat > 90) {
                                                    System.out.println("\n     --- Valor invalido. A latitude vai de -90 a 90 ---\n");
                                                }
                                            } while (lat < -90 || lat > 90);
                                            do {
                                                System.out.println("Indique a longitude do portal:  ");
                                                BufferedReader br8 = new BufferedReader(new InputStreamReader(System.in));
                                                lon = Double.parseDouble(br8.readLine());
                                                if (lon < -180 || lon > 180) {
                                                    System.out.println("\n     --- Valor invalido. A longitude vai de -180 a 180 ---\n");
                                                }
                                            } while (lon < -180 || lon > 180);
                                            do {
                                                System.out.println("Indique a energia máxima do portal:  ");
                                                BufferedReader br10 = new BufferedReader(new InputStreamReader(System.in));
                                                energiaMax = Double.parseDouble(br10.readLine());
                                                if (energiaMax < 0) {
                                                    System.out.println("\n     --- Valor invalido. A latitude vai de -90 a 90 ---\n");
                                                }
                                            } while (energiaMax < 0);
                                            System.out.println("Indique o nome do portal:  ");
                                            BufferedReader br9 = new BufferedReader(new InputStreamReader(System.in));
                                            String name = br9.readLine();
                                            p.addPortal(lat, lon, name, energiaMax, Teams.Neutro);
                                            break;
                                        case 2:
                                            System.out.println("Insire o id do portal que deseja remover:  ");
                                            BufferedReader br12 = new BufferedReader(new InputStreamReader(System.in));
                                            int id4 = Integer.parseInt(br12.readLine());
                                            p.removeLocal(id4);
                                            break;
                                        case 3:
                                            int opcao9;
                                            do {
                                                System.out.println("\n0. Voltar atras");
                                                System.out.println("1. Editar nome");
                                                System.out.println("2. Editar coordenadas");
                                                do {
                                                    BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                                    opcao9 = Integer.parseInt(br3.readLine());
                                                    if (opcao9 < 0 || opcao9 > 2) {
                                                        System.out.println("\n     --- Opcao invalida ---\n");
                                                    }
                                                } while (opcao9 < 0 && opcao9 > 2);

                                                switch (opcao9) {
                                                    case 1:
                                                        System.out.println("\nInsire o id do portal que deseja mudar o nome:  ");
                                                        BufferedReader br4 = new BufferedReader(new InputStreamReader(System.in));
                                                        int id2 = Integer.parseInt(br4.readLine());
                                                        System.out.println("\nInsire o novo nome do portal:  ");
                                                        BufferedReader br5 = new BufferedReader(new InputStreamReader(System.in));
                                                        String str2 = br5.readLine();
                                                        p.updatePortalName(id2, str2);
                                                        break;
                                                    case 2:
                                                        System.out.println("\nInsire o id do portal que deseja mudar as coordenadas:  ");
                                                        BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                                        int id = Integer.parseInt(br3.readLine());
                                                        do {
                                                            System.out.println("Indique a nova latitude do portal:  ");
                                                            BufferedReader br10 = new BufferedReader(new InputStreamReader(System.in));
                                                            lat = Double.parseDouble(br10.readLine());
                                                            if (lat < -90 || lat > 90) {
                                                                System.out.println("\n     --- Valor invalido. A latitude vai de -90 a 90 ---\n");
                                                            }
                                                        } while (lat < -90 || lat > 90);
                                                        do {
                                                            System.out.println("Indique a nova longitude do portal:  ");
                                                            BufferedReader br11 = new BufferedReader(new InputStreamReader(System.in));
                                                            lon = Double.parseDouble(br11.readLine());
                                                            if (lon < -180 || lon > 180) {
                                                                System.out.println("\n     --- Valor invalido. A longitude vai de -180 a 180 ---\n");
                                                            }
                                                        } while (lon < -180 || lon > 180);
                                                        p.updateLocalCoordinates(id, lat, lon);
                                                        break;
                                                }
                                            } while (opcao9 != 0);
                                            break;
                                        case 4:
                                            int opcao12;
                                            System.out.println("0. Voltar atras");
                                            System.out.println("1. Listar portais por energia atual");
                                            System.out.println("2. Listar portais conquistados por jogador");
                                            System.out.println("3. Listar dados gerais dos portais");
                                            do {
                                                BufferedReader br6 = new BufferedReader(new InputStreamReader(System.in));
                                                opcao12 = Integer.parseInt(br6.readLine());
                                                if (opcao12 < 0 || opcao12 > 2) {
                                                    System.out.println("\n     --- Opcao invalida ---\n");
                                                }
                                            } while (opcao12 < 0 && opcao12 > 2);
                                            switch(opcao12) {
                                                case 1:
                                                    p.listarPortaisPorEnergia();
                                                    break;
                                                case 2:
                                                    p.listarPortaisPorOwnership();
                                                    break;
                                                case 3:
                                                    p.listarDadosGeraisPortais();
                                                    break;
                                            }
                                            break;
                                    }
                                } while (opcao7 != 0);
                                break;
                            case 3:
                                int opcao8;
                                do {
                                    System.out.println("\n0. Voltar atras");
                                    System.out.println("1. Adicionar conector");
                                    System.out.println("2. Remover conector");
                                    System.out.println("3. Editar conector");
                                    System.out.println("4. Listar conectores");
                                    do {
                                        BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                        opcao8 = Integer.parseInt(br3.readLine());
                                        if (opcao8 < 0 || opcao8 > 4) {
                                            System.out.println("\n     --- Opcao invalida ---\n");
                                        }
                                    } while (opcao8 < 0 && opcao8 > 4);

                                    switch (opcao8) {
                                        case 1:
                                            double lat, lon, energy;
                                            int cooldown;
                                            do {
                                                System.out.println("Indique a latitude do conector:  ");
                                                BufferedReader br10 = new BufferedReader(
                                                        new InputStreamReader(System.in));
                                                lat = Double.parseDouble(br10.readLine());
                                                if (lat < -90 || lat > 90) {
                                                    System.out.println("\n     --- Valor invalido. A latitude vai de -90 a 90 ---\n");
                                                }
                                            } while (lat < -90 || lat > 90);
                                            do {
                                                System.out.println("Indique a longitude do conector:  ");
                                                BufferedReader br11 = new BufferedReader(
                                                        new InputStreamReader(System.in));
                                                lon = Double.parseDouble(br11.readLine());
                                                if (lon < -180 || lon > 180) {
                                                    System.out.println("\n     --- Valor invalido. A longitude vai de -180 a 180 ---\n");
                                                }
                                            } while (lon < -180 || lon > 180);
                                            do {
                                                System.out.println("Indique a energia do conector:  ");
                                                BufferedReader br30 = new BufferedReader(
                                                        new InputStreamReader(System.in));
                                                energy = Double.parseDouble(br30.readLine());
                                                if (energy < 0) {
                                                    System.out.println("\n     --- Valor invalido. A energia não suporta valores negativos ---\n");
                                                }
                                            } while (energy < 0);
                                            do {
                                                System.out.println("Indique o cooldown do conector:  ");
                                                BufferedReader br31 = new BufferedReader(
                                                        new InputStreamReader(System.in));
                                                cooldown = Integer.parseInt(br31.readLine());
                                                if (cooldown < 0) {
                                                    System.out.println("\n     --- Valor invalido. O cooldown não pode ser negativo ---\n");
                                                }
                                            } while (cooldown < 0);
                                            p.addConnector(lat, lon, energy, cooldown);
                                            break;
                                        case 2:
                                            System.out.println("\nInsire o id do connector que deseja remover:  ");
                                            BufferedReader br13 = new BufferedReader(new InputStreamReader(System.in));
                                            int id5 = Integer.parseInt(br13.readLine());
                                            p.removeLocal(id5);
                                            break;
                                        case 3:
                                            System.out.println("\nInsire o id do connector que deseja mudar as coordenadas:  ");
                                            BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                            int id = Integer.parseInt(br3.readLine());
                                            do {
                                                System.out.println("Indique a nova latitude do conector:  ");
                                                BufferedReader br10 = new BufferedReader(new InputStreamReader(System.in));
                                                lat = Double.parseDouble(br10.readLine());
                                                if (lat < -90 || lat > 90) {
                                                    System.out.println("\n     --- Valor invalido. A latitude vai de -90 a 90 ---\n");
                                                }
                                            } while (lat < -90 || lat > 90);
                                            do {
                                                System.out.println("Indique a nova longitude do conector:  ");
                                                BufferedReader br11 = new BufferedReader(new InputStreamReader(System.in));
                                                lon = Double.parseDouble(br11.readLine());
                                                if (lon < -180 || lon > 180) {
                                                    System.out.println("\n     --- Valor invalido. A longitude vai de -180 a 180 ---\n");
                                                }
                                            } while (lon < -180 || lon > 180);
                                            p.updateLocalCoordinates(id, lat, lon);
                                            break;
                                        case 4:
                                            int opcao13;
                                            System.out.println("0. Voltar atras");
                                            System.out.println("1. Listar dados gerais dos conectores");
                                            System.out.println("2. Listar conectores por menor cooldown");
                                            do {
                                                BufferedReader br6 = new BufferedReader(new InputStreamReader(System.in));
                                                opcao13 = Integer.parseInt(br6.readLine());
                                                if (opcao13 < 0 || opcao13 > 2) {
                                                    System.out.println("\n     --- Opcao invalida ---\n");
                                                }
                                            } while (opcao13 < 0 && opcao13 > 2);
                                            switch(opcao13) {
                                                case 1:
                                                    p.listarDadosGeraisConectores();
                                                    break;
                                                case 2:
                                                    p.listarConnectorMenorCooldown();
                                                    break;
                                            }
                                            break;
                                    }
                                } while (opcao8 != 0);
                                break;
                            case 4:
                                int opcao11;
                                do {
                                    System.out.println("\n0. Voltar atras");
                                    System.out.println("1. Criar uma rota com um sentido");
                                    System.out.println("2. Criar uma rota com dois sentidos");
                                    System.out.println("3. Remover uma rota de um sentido");
                                    System.out.println("4. Remover uma rota de dois sentidos");
                                    System.out.println("5. Exportar ficheiro com dados das rotas");
                                    System.out.println("6. Importar ficheiro com dados das rotas");
                                    do {
                                        BufferedReader br20 = new BufferedReader(new InputStreamReader(System.in));
                                        opcao11 = Integer.parseInt(br20.readLine());
                                        if (opcao11 < 0 || opcao11 > 6) {
                                            System.out.println("\n     --- Opcao invalida ---\n");
                                        }
                                    } while (opcao11 < 0 && opcao11 > 6);
                                    switch (opcao11) {
                                        case 1:
                                            boolean exist;
                                            int id, id2;
                                            if (p.locaisSize() >= 2) {
                                                do {
                                                    System.out.println("Indique o id do local de origem");
                                                    BufferedReader br21 = new BufferedReader(new InputStreamReader(System.in));
                                                    id = Integer.parseInt(br21.readLine());
                                                    exist = p.confirmExistLocalID(id);
                                                    if (exist == false) {
                                                        System.out.println("\n Não existe nenhum local com este id\n");
                                                    }
                                                } while (exist != true);
                                                do {
                                                    System.out.println("Indique o id do local de destino");
                                                    BufferedReader br22 = new BufferedReader(new InputStreamReader(System.in));
                                                    id2 = Integer.parseInt(br22.readLine());
                                                    exist = p.confirmExistLocalID(id2);
                                                    if (exist == false) {
                                                        System.out.println("\n Não existe nenhum local com este id\n");
                                                    }
                                                } while (exist != true);
                                                p.addRotaUmSentido(id, id2);
                                            } else {
                                                throw new NotEnoughObjects("Não existem pelo menos dois locais para que se possa estabelecer uma rota");
                                            }
                                            break;
                                        case 2:
                                            boolean exist2;
                                            int id3, id4;
                                                if (p.locaisSize() >= 2) {
                                                    do {
                                                        System.out.println("Indique o id do primeiro local");
                                                        BufferedReader br21 = new BufferedReader(new InputStreamReader(System.in));
                                                        id3 = Integer.parseInt(br21.readLine());
                                                        exist2 = p.confirmExistLocalID(id3);
                                                        if (exist2 == false) {
                                                            System.out.println("\n Não existe nenhum local com este id\n");
                                                        }
                                                    } while (exist2 == false);
                                                    do {
                                                        System.out.println("Indique o id do segundo local");
                                                        BufferedReader br22 = new BufferedReader(new InputStreamReader(System.in));
                                                        id4 = Integer.parseInt(br22.readLine());
                                                        exist2 = p.confirmExistLocalID(id4);
                                                        if (exist2 == false) {
                                                            System.out.println("\n Não existe nenhum local com este id\n");
                                                        }
                                                    } while (exist2 == false);
                                                    p.addRotaDoisSentidos(id3, id4);
                                                } else {
                                                    throw new NotEnoughObjects("Não existem pelo menos dois locais para que se possa estabelecer uma rota");
                                                }
                                            break;
                                        case 3:
                                            boolean exist3;
                                            int id5, id6;
                                                if (p.locaisSize() >= 2) {
                                                    do {
                                                        System.out.println("Indique o id do local de origem");
                                                        BufferedReader br21 = new BufferedReader(new InputStreamReader(System.in));
                                                        id5 = Integer.parseInt(br21.readLine());
                                                        exist3 = p.confirmExistLocalID(id5);
                                                        if (exist3 == false) {
                                                            System.out.println("\n Não existe nenhum local com este id\n");
                                                        }
                                                    } while (exist3 == false);
                                                    do {
                                                        System.out.println("Indique o id do local de destino");
                                                        BufferedReader br22 = new BufferedReader(new InputStreamReader(System.in));
                                                        id6 = Integer.parseInt(br22.readLine());
                                                        exist3 = p.confirmExistLocalID(id6);
                                                        if (exist3 == false) {
                                                            System.out.println("\n Não existe nenhum local com este id\n");
                                                        }
                                                    } while (exist3 == false);
                                                    p.removeRotaUmSentido(id5, id6);
                                                } else {
                                                    System.out.println("Não existem pelo menos dois locais para que se possa estabelecer uma rota");
                                                }
                                            break;
                                        case 4:
                                            boolean exist4;
                                            int id7, id8;
                                                if (p.locaisSize() >= 2) {
                                                    do {
                                                        System.out.println("Indique o id do primeiro local");
                                                        BufferedReader br21 = new BufferedReader(new InputStreamReader(System.in));
                                                        id7 = Integer.parseInt(br21.readLine());
                                                        exist4 = p.confirmExistLocalID(id7);
                                                        if (exist4 == false) {
                                                            System.out.println("\n Não existe nenhum local com este id\n");
                                                        }
                                                    } while (exist4 == false);
                                                    do {
                                                        System.out.println("Indique o id do segundo local");
                                                        BufferedReader br22 = new BufferedReader(new InputStreamReader(System.in));
                                                        id8 = Integer.parseInt(br22.readLine());
                                                        exist4 = p.confirmExistLocalID(id8);
                                                        if (exist4 == false) {
                                                            System.out.println("\n Não existe nenhum local com este id\n");
                                                        }
                                                    } while (exist4 == false);
                                                    p.removeRotaDoisSentidos(id7, id8);
                                                } else {
                                                    throw new NotEnoughObjects("Não existem pelo menos dois locais para que se possa estabelecer uma rota");
                                                }
                                            break;
                                        case 5:
                                            p.exportToJSONRoutes();
                                            break;
                                        case 6:
                                            System.out.println("\nInsire o nome do ficheiro que quer importar:  ");
                                            BufferedReader br5 = new BufferedReader(new InputStreamReader(System.in));
                                            String str4 = br5.readLine();
                                            str4 += ".json";
                                            p.importPlayersFromJSON(str4);
                                            break;
                                    }
                                } while (opcao11 != 0);
                                break;
                            case 5:
                                int opcao10, id8, id9, id10, id11, id12;
                                boolean exist6, exist7, exist8, exist9, exist10;
                                do {
                                    System.out.println("\n0. Voltar atras");
                                    System.out.println("1. Apresentar o caminho mais curto entre dois pontos");
                                    System.out.println("2. Apresentar o caminho mais curto entre dois pontos passando por um terceiro");
                                    System.out.println("3. Exportar ficheiro com as configuracoes do jogo");
                                    System.out.println("4. Importar ficheiro com as configuracoes do jogo");
                                    System.out.println("5. Exportar ficheiro com os dados dos locais");
                                    System.out.println("6. Importar ficheiro com os dados dos locais");
                                    do {
                                        BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                        opcao10 = Integer.parseInt(br3.readLine());
                                        if (opcao10 < 0 || opcao10 > 7) {
                                            System.out.println("\n     --- Opcao invalida ---\n");
                                        }
                                    } while (opcao10 < 0 && opcao10 > 7);

                                    switch (opcao10) {
                                        case 1:
                                            if (p.locaisSize() >= 2) {
                                                do {
                                                    System.out.println("Indique o id do primeiro local");
                                                    BufferedReader br21 = new BufferedReader(new InputStreamReader(System.in));
                                                    id8 = Integer.parseInt(br21.readLine());
                                                    exist6 = p.confirmExistLocalID(id8);
                                                    if (exist6 == false) {
                                                        System.out.println("\n Não existe nenhum local com este id\n");
                                                    }
                                                } while (exist6 == false);
                                                do {
                                                    System.out.println("Indique o id do segundo local");
                                                    BufferedReader br22 = new BufferedReader(new InputStreamReader(System.in));
                                                    id9 = Integer.parseInt(br22.readLine());
                                                    exist7 = p.confirmExistLocalID(id9);
                                                    if (exist7 == false) {
                                                        System.out.println("\n Não existe nenhum local com este id\n");
                                                    }
                                                } while (exist7 == false);
                                                System.out.println(p.caminhoMaisCurto(id8, id9));
                                            } else {
                                                System.out.println("Não existem pelo menos dois locais para que se possa estabelecer uma rota");
                                            }
                                            break;
                                        case 2:
                                            do {
                                                System.out.println("Indique o id do primeiro local");
                                                BufferedReader br41 = new BufferedReader(new InputStreamReader(System.in));
                                                id10 = Integer.parseInt(br41.readLine());
                                                exist8 = p.confirmExistLocalID(id10);
                                                if (exist8 == false) {
                                                    System.out.println("\n Não existe nenhum local com este id\n");
                                                }
                                            } while (exist8 == false);
                                            do {
                                                System.out.println("Indique o id do segundo local");
                                                BufferedReader br42 = new BufferedReader(new InputStreamReader(System.in));
                                                id11 = Integer.parseInt(br42.readLine());
                                                exist9 = p.confirmExistLocalID(id11);
                                                if (exist9 == false) {
                                                    System.out.println("\n Não existe nenhum local com este id\n");
                                                }
                                            } while (exist9 == false);
                                            do {
                                                System.out.println("Indique o id do local que quer que passe");
                                                BufferedReader br43 = new BufferedReader(new InputStreamReader(System.in));
                                                id12 = Integer.parseInt(br43.readLine());
                                                exist10 = p.confirmExistLocalID(id12);
                                                if (exist10 == false) {
                                                    System.out.println("\n Não existe nenhum local com este ids\n");
                                                }
                                            } while (exist10 == false);
                                            System.out.println(p.camMaisCurtoPor1Vertice(id10, id12, id11));
                                            break;
                                        case 3:
                                            p.exportToJSONGlobal();
                                            break;
                                        case 4:
                                            System.out.println("\nInsire o nome do ficheiro que quer importar:  ");
                                            BufferedReader br5 = new BufferedReader(new InputStreamReader(System.in));
                                            String str3 = br5.readLine();
                                            str3 += ".json";
                                            p.importPlayersFromJSON(str3);
                                            break;
                                        case 5:
                                            p.exportToJSONLocals();
                                            break;
                                        case 6:
                                            System.out.println("\nInsire o nome do ficheiro que quer importar:  ");
                                            BufferedReader br6 = new BufferedReader(new InputStreamReader(System.in));
                                            String str5 = br6.readLine();
                                            str5 += ".json";
                                            p.importLocalsFromJSON(str5);
                                            break;
                                    }
                                } while (opcao10 != 0);
                                break;
                        }
                    } while (opcao2 != 0);
            }
        } while (opcao1 != 0);
    }
}