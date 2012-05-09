tree grammar PhpTree; 
options { 
tokenVocab=Php;
ASTLabelType=CommonTree;
backtrack=true;
output=AST;
}

@header{
    package net.kuruvila.php.parser;
}

prog : statement*;

statement
    : BodyString
    | ^(Block statement*)
    //| UnquotedString Colon statement -> ^(Label UnquotedString statement)
    | classDefinition
    | interfaceDefinition
    | complexStatement
    | simpleStatement
    ;

interfaceDefinition
    : ^(Interface UnquotedString interfaceExtends? interfaceMember*)
    ;

interfaceExtends
    : Extends^ UnquotedString+
    ;
interfaceMember
    : ^(Const UnquotedString atom?)
    | ^(Method ^(Modifiers fieldModifier*) UnquotedString ^(Params paramDef*))
    ;

classDefinition
    :   ^(Class ^(Modifiers classModifier?) UnquotedString (^(Extends UnquotedString))? classImplements? classMember*)
    ;
    
classImplements
    :  ^(Implements UnquotedString+)
    ;

classMember
    : ^(Method ^(Modifiers fieldModifier*) UnquotedString ^(Params paramDef*) statementBlock?)
    | ^(Var ^(Dollar UnquotedString) atom?) 
    | ^(Const UnquotedString atom?)
    | ^(Field ^(Modifiers fieldModifier*) ^(Dollar UnquotedString) atom?)
    ;
    
statementBlock
    : ^(Block statement*)
    ;

fieldDefinition
    : ^(Field ^(Dollar UnquotedString) atom?)
    ;
    
classModifier
    : 'abstract';
    
fieldModifier
    : AccessModifier | 'abstract' | 'static' 
    ;


complexStatement
    : ^('if' expression statement statement?)
    | ^(For forInit forCondition forUpdate statement)
    | ^(Foreach variable arrayEntry statement)
    | ^(While expression statement)
    | ^(Do statement expression)
    | ^(Switch expression cases)
    | functionDefinition
    ;

simpleStatement
    : ^(Echo expression+)
    | ^(Global name+)
    | ^(Static variable atom)
    | ^(Break Integer?)
    | ^(Continue Integer?)
    //| Goto^ UnquotedString
    | ^(Return expression?)
    | ^(RequireOperator expression)
    | expression
    ;


forInit
    : ^(ForInit expression+)
    ;

forCondition
    : ^(ForCondition expression+)
    ;
    
forUpdate
    : ^(ForUpdate expression+)
    ;

cases 
    : casestatement*  defaultcase
    ;

casestatement
    : ^(Case expression statement*)
    ;

defaultcase 
    : ^(Default statement*)
    ;

functionDefinition
    : ^(Function UnquotedString ^(Params paramDef*) ^(Block statement*))
    ;
    
paramDef
    : ^(Equals paramName atom) 
    | paramName
    ;

paramName
    : ^(Dollar UnquotedString)
    | ^(Ampersand ^(Dollar UnquotedString))
    ;
    
expression
    : ^(Or expression expression)
    | ^(Xor expression expression)
    | ^(And expression expression)
    | ^(Equals expression expression)
    | ^(AsignmentOperator expression expression)
    | ^(IfExpression expression expression expression)
    | ^(LogicalOr expression expression)
    | ^(LogicalAnd expression expression)
    | ^(Pipe expression expression)
    | ^(Ampersand expression expression)
    | ^(EqualityOperator expression expression)
    | ^(ShiftOperator expression expression)
    | ^(Plus expression expression)
    | ^(Minus expression expression)
    | ^(Dot expression expression)
    | ^(Asterisk expression expression)
    | ^(Forwardslash expression expression)
    | ^(Percent expression expression)
    | ^(Bang expression)
    | ^(Instanceof expression expression)
    | ^(Tilde expression)
    | ^(Minus expression) 
    | ^(SuppressWarnings expression)
    | ^(Cast PrimitiveType expression)
    | ^(Prefix IncrementOperator name)
    | ^(Postfix IncrementOperator name)
    | ^(New nameOrFunctionCall)
    | ^(Clone name)
    | atomOrReference
    ;


atomOrReference
    : atom
    | reference
    ;

arrayDeclaration
    : ^(Array arrayEntry*)
    ;

arrayEntry
    : (keyValuePair | expression)
    ;

keyValuePair
    : ^(ArrayAssign expression+)
    ;

atom: SingleQuotedString | DoubleQuotedString | HereDoc | Integer | Real | Boolean | arrayDeclaration
    ;

//Need to be smarter with references, they have their own tower of application.
reference
    : ^(Ampersand nameOrFunctionCall)
    | nameOrFunctionCall
    ;

nameOrFunctionCall
    : ^(Apply name expression*)
    | name
    ;

name: staticMemberAccess
    | memberAccess
    | variable
    ;
    
staticMemberAccess
    : ^('::' UnquotedString  variable)
    ;

memberAccess
    : ^(OpenSquareBrace variable expression)
    | ^('->' variable UnquotedString)
    ;
    
variable
    : ^(Dollar variable)
    | UnquotedString
    ;