grammar SSL;


/******************
 ** Parser rules **
 ******************/

root         :   (law|sensor|area|app|NL)+ global? (NL)* EOF ;

// Law
law          :   'law ' (random|markov|function|file) ;
    random   :   'random '   name=BASIC_STRING ' {' NL random_def   '}' ;
    markov   :   'markov '   name=BASIC_STRING ' {' NL markov_def   '}' ;
    function :   'function ' name=BASIC_STRING ' {' NL function_def '}' ;
    file     :   'file '     name=BASIC_STRING ' {' NL file_def     '}' ;

random_def          :   TAB 'values in ' (random_interval|random_list) NL;
    random_interval :   'interval ' interval ;
    random_list     :   'list ' list ;

interval             :  '[' (interval_integer|interval_double) ']' ;
    interval_integer :  min=INTEGER COMMA max=INTEGER ;
    interval_double  :  min=DOUBLE  COMMA max=DOUBLE  ;

markov_def       :   (edge_integer+|edge_double+|edge_boolean+|edge_string+) ;
    edge_integer :   TAB from=INTEGER ' -> ' proba=DOUBLE ' -> ' to=INTEGER NL;
    edge_double  :   TAB from=DOUBLE  ' -> ' proba=DOUBLE ' -> ' to=DOUBLE  NL;
    edge_boolean :   TAB from=BOOLEAN ' -> ' proba=DOUBLE ' -> ' to=BOOLEAN NL;
    edge_string  :   TAB from=STRING  ' -> ' proba=DOUBLE ' -> ' to=STRING  NL;

function_def     :   (caseFcExpr+|caseFcInteger+|caseFcDouble+|caseFcString+|caseFcBoolean+) ;
    caseFcExpr   :   TAB cond=EXPRESSION ' => ' expr=EXPRESSION NL;
    caseFcInteger:   TAB cond=EXPRESSION ' => ' expr=INTEGER    NL;
    caseFcDouble :   TAB cond=EXPRESSION ' => ' expr=DOUBLE     NL;
    caseFcString :   TAB cond=EXPRESSION ' => ' expr=STRING     NL;
    caseFcBoolean:   TAB cond=EXPRESSION ' => ' expr=BOOLEAN    NL;

list             :   '(' (list_integer|list_double|list_boolean|list_string) ')' ;
    list_integer : elem+=INTEGER (COMMA elem+=INTEGER)* ;
    list_double  : elem+=DOUBLE  (COMMA elem+=DOUBLE)* ;
    list_boolean : elem+=BOOLEAN (COMMA elem+=BOOLEAN)* ;
    list_string  : elem+=STRING  (COMMA elem+=STRING)*  ;

file_def            : sensor_name location_def interpolation? NL ;
    sensor_name     : TAB 'named ' name=STRING NL ;
    location_def    : TAB 'from ' location=FILE_LOCATION ' ' (type_json|type_csv) ;
    type_json       : 'json ' uri=STRING header_json? ;
        header_json : NL TAB 'using field ' f1_name=STRING ' as ' f1_type=HEADER_TYPE (COMMA 'field ' f2_name=STRING ' as ' f2_type=HEADER_TYPE (' and field ' f3_name=STRING ' as ' f3_type=HEADER_TYPE)?)? ;
    type_csv        : 'csv ' uri=STRING header_csv? ;
        header_csv  : NL TAB 'using column ' f1_name=(STRING|INTEGER) ' as ' f1_type=HEADER_TYPE (COMMA 'column ' f2_name=(STRING|INTEGER) ' as ' f2_type=HEADER_TYPE (' and column ' f3_name=(STRING|INTEGER) ' as ' f3_type=HEADER_TYPE)?)? ;
    interpolation   : NL TAB 'with linear interpolation' restriction? ;
        restriction : ' restricted to ' interval ;

// Sensor
sensor          : 'sensor ' name=BASIC_STRING ' {' NL sensor_def '}' ;
    sensor_def  : source period? noise? ; // offset?;
        source  : TAB 'governed by ' ref=BASIC_STRING NL ;
        noise   : TAB 'noise ' interval NL ;
//        offset              : TOK_TAB 'offset ' date=DATE '\n' ;
        period  : TAB 'period ' period_value=PERIOD NL ;

// Area
area                        : 'area ' name=BASIC_STRING ' {' NL area_def '}' ;
    area_def                : sensor_group+ ;
        sensor_group        : TAB 'has ' nb=INTEGER ' ' sensor_ref=BASIC_STRING noise_override? NL ;
            noise_override  : ' with noise ' interval ;

// App
app                : 'app ' name=BASIC_STRING ' {' NL app_def '}' ;
    app_def        : area_group+ ;
        area_group : TAB area_ref=BASIC_STRING ': ' list_basic_string NL ;

list_basic_string   : elem+=BASIC_STRING  (COMMA elem+=BASIC_STRING)* ;

// Global
global               : 'global {' NL global_def '}' ;
    global_def       : TAB (realtime|replay) ;
        realtime     : 'realtime' g_offset? NL ;
            g_offset : ' offset ' date=DATE ;
        replay       : 'replay' NL start end ;
            start    : TAB TAB 'start ' date=DATE NL ;
            end      : TAB TAB 'end '   date=DATE NL ;


/*****************
 ** Lexer rules **
 *****************/

COMMENT             :   '\n'* ' '* '#' ~( '\r' | '\n' )*  -> skip;     // Single line comments, starting with a #

FILE_TYPE           :   'json'  | 'csv';
FILE_LOCATION       :   'local' | 'distant';
HEADER_TYPE         :   'time'|'value'|'name';
PERIOD              :   '1'..'9''0'..'9'*('ms'|'s'|'m'|'h'|'d');
DATE                :   DIGIT DIGIT '/' DIGIT DIGIT '/' DIGIT DIGIT DIGIT DIGIT ' ' DIGIT DIGIT ':' DIGIT DIGIT;
BOOLEAN             :   ('true'|'TRUE'|'false'|'FALSE');
INTEGER             :   ('-'|'+')?DIGIT+;
DOUBLE              :   ('-'|'+')?'0'..'9'*'.'?'0'..'9'+;
BASIC_STRING        :   (LETTERS)(LETTERS|'0'..'9'|'_'|'-')*;
EXPRESSION          :   '`' (LETTERS|DIGIT|SYMBOLS)+ '`';
STRING              :   '"'.+?'"';
COMMA               :   ',' ' '?;
TAB                 :   '    ';
NL                  :   '\n';

fragment LETTERS    :   'a'..'z'|'A'..'Z';
fragment DIGIT      :   '0'..'9';
fragment SYMBOLS    :   '('|')'|'/'|'*'|'-'|'+'|'^'|'%'|'='|'!'|'<'|'>'|'&'|'|'|'.'|' ';

