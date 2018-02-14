grammar SSL;


/******************
 ** Parser rules **
 ******************/

root            :   (law|sensor|area|exec|'\n')+ global? EOF ;

// Law
law         :   'law ' (random|markov|function|file) ;
    random  :   'random '   name=BASIC_STRING ' {\n' random_def   '}' ;
    markov  :   'markov '   name=BASIC_STRING ' {\n' markov_def   '}' ;
    function:   'function ' name=BASIC_STRING ' {\n' function_def '}' ;
    file    :   'file  '    name=BASIC_STRING ' {\n' file_def     '}' ;

random_def          :   TOK_TAB 'values in ' (random_interval|random_list) '\n';
    random_interval :   'interval ' interval ;
    random_list     :   'list ' list ;

interval                :  '[' (interval_integer|interval_double) ']' ;
    interval_integer    :  min=INTEGER ',' max=INTEGER ;
    interval_double     :  min=DOUBLE  ',' max=DOUBLE  ;

markov_def      :   (edge_integer+|edge_double+|edge_boolean+|edge_string+) ;
    edge_integer:   TOK_TAB from=INTEGER ' -> ' proba=DOUBLE ' -> ' to=INTEGER '\n';
    edge_double :   TOK_TAB from=DOUBLE  ' -> ' proba=DOUBLE ' -> ' to=DOUBLE  '\n';
    edge_boolean:   TOK_TAB from=BOOLEAN ' -> ' proba=DOUBLE ' -> ' to=BOOLEAN '\n';
    edge_string :   TOK_TAB from=STRING  ' -> ' proba=DOUBLE ' -> ' to=STRING  '\n';

function_def    :   caseFunc+ ;
    caseFunc    :   TOK_TAB cond=EXPRESSION ' => ' expr=EXPRESSION '\n';

list      :   '(' (list_integer|list_double|list_boolean|list_string) ')' ;
    list_integer : elem+=INTEGER (', ' elem+=INTEGER)* ;
    list_double  : elem+=DOUBLE  (', ' elem+=DOUBLE)* ;
    list_boolean : elem+=BOOLEAN (', ' elem+=BOOLEAN)* ;
    list_string  : elem+=STRING  (', ' elem+=STRING)*  ;

list_basic_string  : elem+=BASIC_STRING  (', ' elem+=BASIC_STRING)* ;

file_def            : 'from ' location=FILE_LOCATION ' ' (type_json|type_csv) interpolation? ;
    type_json       : 'json ' name=STRING header_json?;
        header_json : '\n' TOK_TAB 'using ' 'field ' f1_name=STRING ' as ' f1_type=HEADER_TYPE (', field ' f2_name=STRING ' as ' f2_type=HEADER_TYPE (' and field ' f3_name=STRING ' as ' f3_type=HEADER_TYPE)?)?;
    type_csv        : 'csv ' name=STRING header_csv?;
        header_csv  : '\n' TOK_TAB 'using ' 'column ' f1_name=(STRING|INTEGER) ' as ' f1_type=HEADER_TYPE (', column ' f2_name=(STRING|INTEGER) ' as ' f2_type=HEADER_TYPE (' and column ' f3_name=(STRING|INTEGER) ' as ' f3_type=HEADER_TYPE)?)?;
    interpolation   : '\n' TOK_TAB 'with linear interpolation' restriction?;
        restriction : ' restricted to ' interval;

// Sensor
sensor                      : 'sensor ' name=BASIC_STRING ' {\n' sensor_def '}';
    sensor_def              : source period? noise? ; // offset?;
        source              : TOK_TAB 'governed by ' law_ref '\n';
        noise               : TOK_TAB 'noise ' interval '\n';
//        offset              : TOK_TAB 'offset ' date=DATE '\n';
        period              : TOK_TAB 'period ' period_value=PERIOD '\n';

law_ref : 'law ' ref=BASIC_STRING;

// Area
area                        : 'area ' name=BASIC_STRING ' {\n' area_def '}'  ;
    area_def                : sensor_group+ ;
        sensor_group        : TOK_TAB 'has ' nb=INTEGER ' ' sensor_ref=BASIC_STRING noise_override? '\n';
            noise_override  : ' with noise ' interval;

// App
exec                : 'app ' name=BASIC_STRING ' {\n' exec_def '}' ;
    exec_def        : area_group+ ;
        area_group  : TOK_TAB area_ref=BASIC_STRING ': ' list_basic_string '\n';

// Global
global          : 'global {\n' global_def '}' ;
    global_def  : TOK_TAB (realtime|replay);
    realtime    : 'realtime' globalOffset? '\n';
    globalOffset: ' offset ' date=DATE;
    replay      : 'replay\n' start end;
    start       : TOK_TAB TOK_TAB 'start ' date=DATE '\n';
    end         : TOK_TAB TOK_TAB 'end ' date=DATE '\n';

/*****************
 **    Token    **
 *****************/

TOK_LAW         : 'law';
TOK_RANDOM      : 'random';
TOK_MARKOV      : 'markov';
TOK_FUNCTION    : 'function';
TOK_VALUES_IN   : 'values in';
TOK_INTERVAL    : 'interval';
TOK_LIST        : 'list';

TOK_SENSOR      : 'sensor';
TOK_FROM        : 'from';
TOK_BY          : 'by';
TOK_FILE        : 'file';
TOK_GOVERNED    : 'governed';
TOK_USING       : 'using';
TOK_WITH        : 'with';
TOK_COLUMN      : 'column';
TOK_FIELD       : 'field';
TOK_LINEAR_INT  : 'linear interpolation';
TOK_RESTRICTED  : 'restricted to';
TOK_NOISE       : 'noise';
TOK_OFFSET      : 'offset';
TOK_AS          : 'as';
TOK_PERIOD      : 'period';

TOK_AREA        : 'area';
TOK_EXEC        : 'app';
TOK_GLOBAL      : 'global';
TOK_REALTIME    : 'realtime';
TOK_REPLAY      : 'replay';
TOK_START       : 'start';
TOK_END         : 'end';

TOK_BR_OP       : '{';
TOK_BR_CL       : '}';

TOK_TAB         : '    ';


/*************
 ** Helpers **
 *************/
//WS          : ' '+                      -> skip;
//NEWLINE     : ('\r'? '\n' | '\r')+      -> skip;
COMMENT     : ' '* '#' ~( '\r' | '\n' )*     -> skip;     // Single line comments, starting with a #

/*****************
 ** Lexer rules **
 *****************/

FILE_TYPE       :   'json'  | 'csv';
FILE_LOCATION   :   'local' | 'distant';
HEADER_TYPE     :   'time'|'value'|'name';

PERIOD          :   '1'..'9''0'..'9'*('ms'|'s'|'m'|'h'|'d');

DATE            :   DIGITS DIGITS '/' DIGITS DIGITS '/' DIGITS DIGITS DIGITS DIGITS ' ' DIGITS DIGITS ':' DIGITS DIGITS;
BOOLEAN         :   ('true'|'TRUE'|'false'|'FALSE');
BASIC_STRING    :   (LETTERS)(LETTERS|'0'..'9'|'_'|'-')*;
INTEGER         :   ('-'|'+')?UINT;
fragment UINT   :   '0'..'9'+;
DOUBLE          :   ('-'|'+')?'0'..'9'*'.'?'0'..'9'+;
//VAR             :   (INTEGER|DOUBLE|BASIC_STRING|STRING|BOOLEAN);
STRING          :   '"'.+?'"';
//NUMBER          :   (DOUBLE|INTEGER);
//EXTENDED_VAR    :   (INTEGER|DOUBLE|EXPRESSION|BOOLEAN);

EXPRESSION    :   '`' (LETTERS|DIGITS|SYMBOLS)+ '`';
//EXPRESSION_LEFT     :   '\t' (LETTERS|DIGITS|SYMBOLS)+ ' ';
//EXPRESSION_RIGHT    :   ' ' (LETTERS|DIGITS|SYMBOLS)+ '\n';
fragment LETTERS        :   'a'..'z'|'A'..'Z';
fragment DIGITS         :   '0'..'9';
fragment SYMBOLS        :   '('|')'|'/'|'*'|'-'|'+'|'^'|'%'|'='|'!'|'<'|'>'|'&'|'|'|'.'|' ';

