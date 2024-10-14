package org.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
public class RombokProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Rombok.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_17;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Rombok.class);

        for (Element element : elements) {
            if(element.getKind() != ElementKind.CLASS) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "annotation should use on only class" + element.getSimpleName());
            }

            //클래스 이름
            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getSimpleName().toString() + "Generator";

            // 클래스 빌더를 정의
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC);

            //엘리먼트 요소를 전부 가져온다
            List<? extends Element> elementList = ElementFilter.fieldsIn(typeElement.getEnclosedElements());

            //요소 중에서 필드 요소만 처리
            for (Element e : elementList) {
                if(e.getKind() != ElementKind.FIELD) continue;

                //필드 이름
                String fieldName = e.getSimpleName().toString();
                //필드 타입
                TypeMirror fieldType = e.asType();

                FieldSpec filed = FieldSpec.builder(TypeName.get(fieldType), fieldName, Modifier.PUBLIC).build();

                //getter 메소드 스펙 작성
                MethodSpec getter = MethodSpec.methodBuilder("get" + parseToCamelCase(fieldName))
                        .addModifiers(Modifier.PUBLIC)
                        .returns(TypeName.get(fieldType))
                        .addStatement("return this.$N", fieldName)
                        .build();

                //필드 추가
                classBuilder.addField(filed);

                classBuilder.methodSpecs.add(getter);

            }

            Filer filer = processingEnv.getFiler();

            try {
                JavaFile javaFile = JavaFile.builder(processingEnv.getElementUtils().getPackageOf(typeElement).toString(), classBuilder.build()).build();
                javaFile.writeTo(filer);
            } catch (IOException exception) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, exception.getMessage());
            }
        }

        return true;
    }

    private String parseToCamelCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
