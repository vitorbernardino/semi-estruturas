import java.util.Scanner;

// Classe que representa uma música
class Music {
    String title;
    String artist;
    String album;
    int duration; // duração em segundos

    public Music(String title, String artist, String album, int duration) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Título: " + title + ", Artista: " + artist + ", Álbum: " + album + ", Duração: " + duration + " segundos";
    }
}

// Classe que representa o nó da lista duplamente encadeada
class Node {
    Music music;
    Node prev;
    Node next;

    public Node(Music music) {
        this.music = music;
        this.prev = null;
        this.next = null;
    }
}

// Classe que implementa a lista duplamente encadeada
class DoublyLinkedList {
    private Node head;
    private Node tail;
    private Node current; // Música atual para navegação

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.current = null;
    }

    // Adicionar música no início
    public void addFirst(Music music) {
        Node newNode = new Node(music);
        if (head == null) {
            head = tail = current = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // Adicionar música no final
    public void addLast(Music music) {
        Node newNode = new Node(music);
        if (tail == null) {
            head = tail = current = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Adicionar música em uma posição específica
    public void addAtPosition(Music music, int position) {
        Node newNode = new Node(music);
        if (position == 0) {
            addFirst(music);
            return;
        }

        Node temp = head;
        int index = 0;
        while (temp != null && index < position - 1) {
            temp = temp.next;
            index++;
        }

        if (temp != null) {
            newNode.next = temp.next;
            if (temp.next != null) {
                temp.next.prev = newNode;
            }
            temp.next = newNode;
            newNode.prev = temp;
        } else {
            addLast(music);
        }
    }

    // Remover música
    public void remove(Music music) {
        Node temp = head;
        while (temp != null) {
            if (temp.music.equals(music)) {
                if (temp.prev != null) {
                    temp.prev.next = temp.next;
                } else {
                    head = temp.next;
                }
                if (temp.next != null) {
                    temp.next.prev = temp.prev;
                } else {
                    tail = temp.prev;
                }
                break;
            }
            temp = temp.next;
        }
    }

    // Listar todas as músicas
    public void listAll() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.music);
            temp = temp.next;
        }
    }

    // Próxima música
    public void next() {
        if (current != null && current.next != null) {
            current = current.next;
            System.out.println("Tocando: " + current.music);
        } else {
            System.out.println("Você já está na última música.");
        }
    }

    // Música anterior
    public void previous() {
        if (current != null && current.prev != null) {
            current = current.prev;
            System.out.println("Tocando: " + current.music);
        } else {
            System.out.println("Você está na primeira música.");
        }
    }

    // Ordenar playlist por título ou artista
    public void sort(String criterion) {
        if (head == null) return;

        Node temp;
        Node iNode;
        Music tempMusic;

        // Bubble Sort baseado no critério de ordenação
        for (temp = head; temp != null; temp = temp.next) {
            for (iNode = temp.next; iNode != null; iNode = iNode.next) {
                if ((criterion.equals("titulo") && temp.music.title.compareTo(iNode.music.title) > 0) ||
                        (criterion.equals("artista") && temp.music.artist.compareTo(iNode.music.artist) > 0)) {
                    tempMusic = temp.music;
                    temp.music = iNode.music;
                    iNode.music = tempMusic;
                }
            }
        }

        System.out.println("Playlist ordenada por " + criterion);
    }

    // Tocar a música atual
    public void play() {
        if (current != null) {
            System.out.println("Tocando: " + current.music);
        } else {
            System.out.println("Nenhuma música para tocar.");
        }
    }

    // Definir a música atual
    public void setCurrent(Music music) {
        Node temp = head;
        while (temp != null) {
            if (temp.music.equals(music)) {
                current = temp;
                break;
            }
            temp = temp.next;
        }
    }

    public Node getHead() {
        return head;
    }
}

public class MusicPlayer {
    private static Scanner scanner = new Scanner(System.in);
    private static DoublyLinkedList playlist = new DoublyLinkedList();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nBem-vindo ao seu Gerenciador de Músicas!");
            System.out.println("1. Próxima música");
            System.out.println("2. Música anterior");
            System.out.println("3. Ordenar playlist");
            System.out.println("4. Tocar música");
            System.out.println("5. Adicionar música");
            System.out.println("6. Remover música");
            System.out.println("7. Listar músicas");
            System.out.println("8. Sair");
            System.out.print("Digite a opção desejada: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de linha

            switch (choice) {
                case 1:
                    playlist.next();
                    break;
                case 2:
                    playlist.previous();
                    break;
                case 3:
                    System.out.print("Ordenar por (titulo/artista): ");
                    String criterion = scanner.nextLine();
                    playlist.sort(criterion);
                    break;
                case 4:
                    playlist.play();
                    break;
                case 5:
                    System.out.println("Escolha onde adicionar a música:");
                    System.out.println("1. No início");
                    System.out.println("2. No final");
                    System.out.println("3. Em uma posição específica");
                    System.out.print("Digite a opção: ");
                    int positionChoice = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer de linha
                    System.out.print("Digite o título da música: ");
                    String title = scanner.nextLine();
                    System.out.print("Digite o artista da música: ");
                    String artist = scanner.nextLine();
                    System.out.print("Digite o álbum da música: ");
                    String album = scanner.nextLine();
                    System.out.print("Digite a duração da música (em segundos): ");
                    int duration = scanner.nextInt();
                    Music music = new Music(title, artist, album, duration);

                    if (positionChoice == 1) {
                        playlist.addFirst(music);
                    } else if (positionChoice == 2) {
                        playlist.addLast(music);
                    } else if (positionChoice == 3) {
                        System.out.print("Digite a posição: ");
                        int position = scanner.nextInt();
                        playlist.addAtPosition(music, position);
                    }
                    break;
                case 6:
                    System.out.print("Digite o título da música para remover: ");
                    String removeTitle = scanner.nextLine();
                    Music removeMusic = null;
                    Node temp = playlist.getHead();
                    while (temp != null) {
                        if (temp.music.title.equals(removeTitle)) {
                            removeMusic = temp.music;
                            break;
                        }
                        temp = temp.next;
                    }
                    if (removeMusic != null) {
                        playlist.remove(removeMusic);
                        System.out.println("Música removida.");
                    } else {
                        System.out.println("Música não encontrada.");
                    }
                    break;
                case 7:
                    playlist.listAll();
                    break;
                case 8:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
