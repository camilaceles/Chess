
import java.util.Scanner;  
import model.*;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		Board board = Board.getBoard();
		board.print();
		
		System.out.printf("%n%n%n------------------------------------------");
		System.out.printf(" GAME STARTED ");
		System.out.printf("------------------------------------------%n%n%n");
		
		int origRow, origCol, destRow, destCol;
		System.out.println("Digite a linha e coluna de origem");
		origRow = scan.nextInt();
		origCol = scan.nextInt();
		
		while (origRow >= 0) {
			System.out.println("Possiveis movimentos da peca selecionada:");
			System.out.println(board.getPossibleMoves(origRow, origCol));
			
			System.out.println("Digite a linha e coluna de destino");
			destRow = scan.nextInt();
			destCol = scan.nextInt();
			if (origRow<8 && origCol>=0 && origCol<8 && destRow>=0 && destRow<8 && destCol>=0 && destCol<8)
				move(origRow, origCol, destRow, destCol);
			else
				System.out.println("Posição inválida");
			
			System.out.println("Digite a linha e coluna de origem");
			origRow = scan.nextInt();
			origCol = scan.nextInt();
		}
		scan.close();
	}
	
	public static void move(int origRow, int origCol, int destRow, int destCol) {
		Board board = Board.getBoard();
		boolean moved = board.movePiece(origRow, origCol, destRow, destCol);;
		
		if (moved) {
			System.out.printf("MOVED: (%d, %d)=>(%d, %d)%n", origRow, origCol, destRow, destCol);
		} else {
			System.out.println("IMPOSSIBLE MOVE");
		}
		board.print();
		System.out.printf("%n%n%n");
	}

}
