/* Validator.java */
/* Generated By:JavaCC: Do not edit this line. Validator.java */
package dev.markmcd.controller.ctl.Validator;

/**
* The Validator class checks the syntax of the CTL formulas and makes sure they are well formed (contain no syntax errors). This Validator.java file is automatically generated from the Validator.jj file using JavaCC (using the terminal line `javacc Validator.jj`) inside this folder. That line also generates all the other files in this directory.
* The CTL parser rules approach from https://github.com/pedrogongora/antelope/blob/master/AntelopeCore/src/antelope/ctl/parser/CTLParser.jj, accessed 9/20
* The Validator class is like an extremely lightweight version of the Parser.java class - it runs through the SAT algorithms, but only through their structure, not their internal logic.
* A compiler was used (the javaCC compiler specifically) so infinitely nested CTL formulas could be parsed correctly.
*/
public class Validator implements ValidatorConstants {

/**
* Root production. This is the method called to kick off the formula validation
*/
  final public void Validate() throws ParseException {
    formula();
    jj_consume_token(0);
}

/**
* A formula can be an expression or an expression with a binary operator
*/
  final public void formula() throws ParseException {
    expression();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AND:
    case OR:
    case IMPLIES:{
      binaryPredicate();
      break;
      }
    default:
      jj_la1[0] = jj_gen;
      ;
    }
}

/**
* An expression can be: an atom, a not operation, a formula with parentheses around it or a temporal expression
*/
  final public void expression() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ATOM:{
      jj_consume_token(ATOM);
      break;
      }
    case NOT:{
      jj_consume_token(NOT);
      formula();
      break;
      }
    case LPAREN:{
      jj_consume_token(LPAREN);
      formula();
      jj_consume_token(RPAREN);
      break;
      }
    case AX:
    case AF:
    case AG:
    case EX:
    case EF:
    case EG:
    case A:
    case E:{
      temporalExpression();
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

/**
* A binary predicate can be: an and operation, an or operation or an implies operation
*/
  final public void binaryPredicate() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AND:{
      jj_consume_token(AND);
      formula();
      break;
      }
    case OR:{
      jj_consume_token(OR);
      formula();
      break;
      }
    case IMPLIES:{
      jj_consume_token(IMPLIES);
      formula();
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

/**
* A temporal expression can be the following operations: AX, AF, AG, EX, EF, EG, EU or AU
*/
  final public void temporalExpression() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AX:{
      jj_consume_token(AX);
      formula();
      break;
      }
    case AF:{
      jj_consume_token(AF);
      formula();
      break;
      }
    case AG:{
      jj_consume_token(AG);
      formula();
      break;
      }
    case EX:{
      jj_consume_token(EX);
      formula();
      break;
      }
    case EF:{
      jj_consume_token(EF);
      formula();
      break;
      }
    case EG:{
      jj_consume_token(EG);
      formula();
      break;
      }
    case A:{
      jj_consume_token(A);
      jj_consume_token(LPAREN);
      formula();
      jj_consume_token(U);
      formula();
      jj_consume_token(RPAREN);
      break;
      }
    case E:{
      jj_consume_token(E);
      jj_consume_token(LPAREN);
      formula();
      jj_consume_token(U);
      formula();
      jj_consume_token(RPAREN);
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  /** Generated Token Manager. */
  public ValidatorTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
	   jj_la1_init_0();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x1c0,0x15fe20,0x1c0,0x1fe00,};
	}

  /** Constructor with InputStream. */
  public Validator(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Validator(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ValidatorTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Validator(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ValidatorTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ValidatorTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Validator(ValidatorTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ValidatorTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[21];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 4; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 21; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
