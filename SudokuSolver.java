import java.util.StringTokenizer; 
import java.util.Scanner; 
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.lang.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import aima.core.logic.propositional.inference.DPLL;
import aima.core.logic.propositional.kb.KnowledgeBase;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Literal;
import aima.core.logic.propositional.kb.data.Model;
import aima.core.logic.propositional.parsing.ast.Connective;
import aima.core.logic.propositional.parsing.ast.ComplexSentence;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import aima.core.logic.propositional.inference.WalkSAT;

public class SudokuSolver{
	
	

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);

		String strategy = args[0];

		String inputFile = args[1];

		String outputFile = args[2];

		String tmp;

		BufferedReader br = null;

		PrintWriter pw = null;

		KnowledgeBase kb;

		try {
			br = new BufferedReader(new FileReader(inputFile));

			tmp = br.readLine();

			int len = Integer.parseInt(tmp); 

			kb = new KnowledgeBase();

			List<PropositionSymbol> psTemp = new ArrayList<PropositionSymbol>();

			for(int i = 0; i < len; i++){
				tmp = br.readLine();
				String[] cell 	= tmp.split(" ");
				for(int j = 0; j < len; j++){
					for(int k = 0; k < len; k++){
						String n = (k + 1 + "");
						String r = (i + 1 + "");
						String c = (j + 1 + "");
						psTemp.add(new PropositionSymbol("s" + r + c + n));
						if(cell[j].equals(n)){
							Sentence s = psTemp.get(psTemp.size()-1);
							kb.tell(s);               //add unit clauses
						}
					}
				}
			}

			// There is at least one number in each entry:
			for(int x = 0; x < len; x++){
				for(int y = 0; y < len; y++){
					Sentence[] sen = new Sentence[len]; 
					for(int z = 0; z < len; z++){
						sen[z] = (new PropositionSymbol("s" + (x+1) + (y+1) + (z+1)));
					}
					ComplexSentence cs = orAll(sen);
					kb.tell(cs);
				} 
			}

			// Each number appears at most once in each row:
			for(int y = 0; y < len; y++){
				for(int z = 0; z < len; z++){
					for(int x = 0; x < len-1; x++){
						for(int i = x + 1; i < len; i++){
							Sentence s1 = new PropositionSymbol("s" + (x+1) + (y+1) + (z+1));
							Sentence s2 = new PropositionSymbol("s" + (i+1) + (y+1) + (z+1));
							Sentence cs1 = new ComplexSentence(Connective.NOT, s1);
							Sentence cs2 = new ComplexSentence(Connective.NOT, s2);
							Sentence cs3 = new ComplexSentence(cs1, Connective.OR, cs2);
							kb.tell(cs3);
						}
					}
				}
			}

			//  Each number appears at most once in each column:
			for(int x = 0; x < len; x++){
				for(int z = 0; z < len; z++){
					for(int y = 0; y < len-1; y++){
						for(int i = y + 1; i < len; i++){
							Sentence s1 = new PropositionSymbol("s" + (x+1) + (y+1) +  (z+1));
							Sentence s2 = new PropositionSymbol("s" + (x+1) + (i+1) +  (z+1));
							Sentence cs1 = new ComplexSentence(Connective.NOT, s1);
							Sentence cs2 = new ComplexSentence(Connective.NOT, s2);
							Sentence cs3 = new ComplexSentence(cs1, Connective.OR, cs2);
							kb.tell(cs3);
						}
					}
				}
			}

			// Each number appears at most once in each 3x3 sub-grid:
			for(int z = 0; z < len; z++){
				for(int i = 0; i < ((int) Math.sqrt(len)); i++){
					for(int j = 0; j < ((int) Math.sqrt(len)); j++){
						for(int x = 0; x < ((int) Math.sqrt(len)); x++){
							for(int y = 0; y < ((int) Math.sqrt(len))-1; y++){
								for(int k = y + 1; k < ((int) Math.sqrt(len)); k++){
									Sentence s1 = new PropositionSymbol("s" + (((int) Math.sqrt(len)*i)+x+1) + (((int) Math.sqrt(len)*j)+y+1) + (z+1));
									Sentence s2 = new PropositionSymbol("s" + (((int) Math.sqrt(len)*i)+x+1) + (((int) Math.sqrt(len)*j)+k+1) + (z+1));
									Sentence cs1 = new ComplexSentence(Connective.NOT, s1);
									Sentence cs2 = new ComplexSentence(Connective.NOT, s2);
									Sentence cs3 = new ComplexSentence(cs1, Connective.OR, cs2);
									kb.tell(cs3);
								}
							}
						}
					}
				}
			}

			for(int z = 0; z < len; z++){
				for(int i = 0; i < ((int) Math.sqrt(len)); i++){
					for(int j = 0; j < ((int) Math.sqrt(len)); j++){
						for(int x = 0; x < ((int) Math.sqrt(len)); x++){
							for(int y = 0; y < ((int) Math.sqrt(len)); y++){
								for(int k = x + 1; k < ((int) Math.sqrt(len)); k++){
									for(int l = 0; l < ((int) Math.sqrt(len)); l++){
										Sentence s1 = new PropositionSymbol("s" + (((int) Math.sqrt(len)*i)+x+1) + (((int) Math.sqrt(len)*j)+y+1) + (z+1));
										Sentence s2 = new PropositionSymbol("s" + (((int) Math.sqrt(len)*i)+x+1) + (((int) Math.sqrt(len)*j)+l+1) + (z+1));
										Sentence cs1 = new ComplexSentence(Connective.NOT, s1);
										Sentence cs2 = new ComplexSentence(Connective.NOT, s2);
										Sentence cs3 = new ComplexSentence(cs1, Connective.OR, cs2);
										kb.tell(cs3);
									}
								}
							}
						}
					}
				}
			}

			// There is at most one number in each entry:
			for(int x = 0; x < len; x++){
				for(int y = 0; y < len; y++){
					for(int z = 0; z < len-1; z++){
						for(int i = z + 1; i < len; i++){
							Sentence s1 = new PropositionSymbol("s" + (x+1) + (y+1) + (z+1));
							Sentence s2 = new PropositionSymbol("s" + (x+1) + (y+1) + (i+1));
							Sentence cs1 = new ComplexSentence(Connective.NOT, s1);
							Sentence cs2 = new ComplexSentence(Connective.NOT, s2);
							Sentence cs3 = new ComplexSentence(cs1, Connective.OR, cs2);
							kb.tell(cs3);
						}
					}
				}
			}


			//  Each number appears at least once in each row:
			for(int y = 0; y < len; y++){
				for(int z = 0; z < len; z++){
					Sentence[] sen = new Sentence[len]; 
					for(int x = 0; x < len; x++){
						sen[x] = (new PropositionSymbol("s" + (x+1) + (y+1) +  (z+1)));
					}
					ComplexSentence cs = orAll(sen);
					kb.tell(cs);
				} 
			}

			//  Each number appears at least once in each column:
			for(int x = 0; x < len; x++){
				for(int z = 0; z < len; z++){
					Sentence[] sen = new Sentence[len]; 
					for(int y = 0; y < len; y++){
						sen[y] = (new PropositionSymbol("s" + (x+1) + (y+1) +  (z+1)));
					}
					ComplexSentence cs = orAll(sen);
					kb.tell(cs);
				} 
			}

			// Each number appears at least once in each 3×3 sub-grid:
			// for(int y = 0; y < ((int) Math.sqrt(len)); y++){
			// 	for(int i = 0; i < ((int) Math.sqrt(len)); i++){
			// 		for(int j = 0; j < ((int) Math.sqrt(len)); j++){
			// 			for(int x= 0; x < ((int) Math.sqrt(len)); x++){
			// 				Sentence[] sen = new Sentence[len]; 
			// 				for(int z = 0; z < len; z++){
			// 					sen[z] = new PropositionSymbol("s" + (((int) Math.sqrt(len)*i)+x+1) + (((int) Math.sqrt(len)*j)+y+1) + (z+1));
			// 				}
			// 				ComplexSentence cs = orAll(sen);
			// 				kb.tell(cs);
			// 			}	
			// 		}
			// 	}
			// }

			// Each number appears at least once in each 3×3 sub-grid:
			// ComplexSentence[] temp = new ComplexSentence[len];
			// for(int z = 1; z <= len; z++){
			// 	Sentence[][] sen = new Sentence[len][len]; 
			// 	for(int i = 1; i <= ((int) Math.sqrt(len)); i++){
			// 		for(int j = 1; j <= ((int) Math.sqrt(len)); j++){
			// 			for(int x = 1; x <= ((int) Math.sqrt(len)); x++){
			// 				for(int y = 1; y <= ((int) Math.sqrt(len)); y++){
			// 					sen[(x*i)-1][(y*j)-1] = new PropositionSymbol("s" + (((int) Math.sqrt(len)*i)+x) + (((int) Math.sqrt(len)*j)+y) + (z));
			// 				}
			// 			}	
			// 			temp[(i*j)-1] = andAll(sen[(i*j)-1]);
			// 		}
			// 	}
				
			// }
			// ComplexSentence csTemp = orAll(temp);
			// kb.tell(csTemp);

			ComplexSentence[] csTemp = new ComplexSentence[len]; 
			for(int z = 1; z <= len; z++){
				Sentence[] temp = new Sentence[len*len];
				int counter = 0;
				for(int i = 0; i < (int) Math.sqrt(len); i++){
					for(int j = 0; j < (int) Math.sqrt(len); j++){
						for(int x = 1; x <= (int) Math.sqrt(len); x++){
							for(int y = 1; y <= (int) Math.sqrt(len); y++){
								temp[counter] = new PropositionSymbol("s" + ((int) Math.sqrt(len)*i+x) + ((int) Math.sqrt(len)*j+z) + z);
								counter++;
							}
						}
					}
				}
				csTemp[z-1] = andAll(temp);
			}		
			kb.tell(orAll(csTemp));

			// for(int z = 0; z < len; z++){
			// 	Sentence[] sen = new Sentence[len]; 
			// 	for(int i = 0; i < ((int) Math.sqrt(len)); i++){
			// 		for(int j = 0; j < ((int) Math.sqrt(len)); j++){
			// 			for(int x = 0; x < ((int) Math.sqrt(len)); x++){
			// 				for(int y = 0; y < ((int) Math.sqrt(len)); y++){
			// 					sen[(((y+1)*(x+1))-1)] = new PropositionSymbol("s" + (((int) Math.sqrt(len)*i)+x+1) + (((int) Math.sqrt(len)*j)+y+1) + (z+1));
			// 				}
			// 			}
			// 		}
			// 	}
			// 	ComplexSentence cs = orAll(sen);
			// 	kb.tell(cs);
			// }

			FileWriter writer = new FileWriter(outputFile);

			if(strategy.equals("dpll")){
				DPLLMod dpll = new DPLLMod();

				System.out.println(kb);

				Model m = null;

				dpll.dpllSatisfiable(kb.asSentence());

				m = dpll.getModel();

				writer.write(m);
			}
			else if(strategy.equals("walksat")){
				WalkSAT walkSAT = new WalkSAT();
				Model m = walkSAT.walkSAT(ConvertToConjunctionOfClauses.convert(kb.asSentence()).getClauses(), 0.5, -1);
				writer.write(m);
			}


		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}	

//(((i+1)*(j+1)*(k+1))-1)%


	public static ComplexSentence orAll(Sentence[] sen) {
		int len = sen.length;
		if (len > 2){
			return (new ComplexSentence(	orAll(Arrays.copyOfRange(sen, 0, (len/2))), 
													Connective.OR, 
													orAll(Arrays.copyOfRange(sen, (len/2), len))));
		}
		else if (len == 2){
			return (new ComplexSentence(sen[0], Connective.OR, sen[1]));
		}
		else{
			return (new ComplexSentence(sen[0], Connective.OR, sen[0]));
		}
	}

	public static ComplexSentence andAll(Sentence[] sen) {
		int len = sen.length;
		if (len > 2){
			return (new ComplexSentence(andAll(Arrays.copyOfRange(sen, 0, (len/2))), 
										Connective.AND, 
										andAll(Arrays.copyOfRange(sen, (len/2), len))));
		}
		else if (len == 2){
			return (new ComplexSentence(sen[0], Connective.AND, sen[1]));
		}
		else{
			return (new ComplexSentence(sen[0], Connective.AND, sen[0]));
		}
	}


	public static String[] crossProduct(String x, String y){
		String[] ret = new String[x.length() * y.length()];
		for (int i = 0; i < x.length()-1; i++){
			for (int j = 0; j < y.length()-1; j++){
				ret[(i+1)*(j+1)-1] = x.charAt(i) + "" +  y.charAt(j);
			}
		}
		return ret;
	}
}