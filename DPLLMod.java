
import aima.core.logic.propositional.inference.DPLL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import aima.core.util.Util;
import aima.core.util.datastructure.Pair;

import aima.core.logic.propositional.kb.KnowledgeBase;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Literal;
import aima.core.logic.propositional.kb.data.Model;
import aima.core.logic.propositional.parsing.ast.ComplexSentence;
import aima.core.logic.propositional.parsing.ast.Connective;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import aima.core.logic.propositional.visitors.SymbolCollector;
import aima.core.logic.propositional.inference.DPLL;
import aima.core.logic.propositional.inference.DPLLSatisfiable;


public class DPLLMod extends DPLLSatisfiable {
	private Model model = null;
	

	public Model getModel(){
		return model;
	} 


	@Override
	public boolean dpllSatisfiable(Sentence s) {
		// clauses <- the set of clauses in the CNF representation of s
		Set<Clause> clauses = ConvertToConjunctionOfClauses.convert(s)
				.getClauses();
		// symbols <- a list of the proposition symbols in s
		List<PropositionSymbol> symbols = getPropositionSymbolsInSentence(s);

		// return DPLL(clauses, symbols, {})
		return dpll(clauses, symbols, new Model());
	}

	@Override
	public boolean dpll(Set<Clause> clauses, List<PropositionSymbol> symbols,
			Model model) {
		// if every clause in clauses is true in model then return true
		if (everyClauseTrue(clauses, model)) {
			System.out.println("ketemu");
			this.model = model;
			return true;
		}
		// if some clause in clauses is false in model then return false
		if (someClauseFalse(clauses, model)) {
			return false;
		}

		// P, value <- FIND-PURE-SYMBOL(symbols, clauses, model)
		Pair<PropositionSymbol, Boolean> pAndValue = findPureSymbol(symbols,
				clauses, model);
		// if P is non-null then
		if (pAndValue != null) {
			// return DPLL(clauses, symbols - P, model U {P = value})
			return dpll(clauses, minus(symbols, pAndValue.getFirst()),
					model.union(pAndValue.getFirst(), pAndValue.getSecond()));
		}

		// P, value <- FIND-UNIT-CLAUSE(clauses, model)
		pAndValue = findUnitClause(clauses, model);
		// if P is non-null then
		if (pAndValue != null) {
			// return DPLL(clauses, symbols - P, model U {P = value})
			return dpll(clauses, minus(symbols, pAndValue.getFirst()),
					model.union(pAndValue.getFirst(), pAndValue.getSecond()));
		}

		// P <- FIRST(symbols); rest <- REST(symbols)
		PropositionSymbol p = Util.first(symbols);
		List<PropositionSymbol> rest = Util.rest(symbols);
		// return DPLL(clauses, rest, model U {P = true}) or
		// ...... DPLL(clauses, rest, model U {P = false})
		return dpll(clauses, rest, model.union(p, true))
				|| dpll(clauses, rest, model.union(p, false));
	}
}