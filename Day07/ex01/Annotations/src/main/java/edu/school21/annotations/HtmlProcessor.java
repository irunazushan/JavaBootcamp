package edu.school21.annotations;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm", "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        for (Element el : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            try {
                createHtmlResource(el);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void createHtmlResource(Element el) throws IOException {
        HtmlForm htmlForm = el.getAnnotation(HtmlForm.class);
        String fileName = htmlForm.fileName();
        String action = htmlForm.action();
        String method = htmlForm.method();
        StringBuilder formBuilder = new StringBuilder();
        formBuilder.append(String.format("<form action=\"%s\" method=\"%s\">\n", action, method));

        for (Element enclosedEl : el.getEnclosedElements()) {
            HtmlInput htmlInput = enclosedEl.getAnnotation(HtmlInput.class);
            if (htmlInput != null) {
                String type = htmlInput.type();
                String name = htmlInput.name();
                String placeholder = htmlInput.placeholder();
                formBuilder.append(String.format("    <input type=\"%s\" name=\"%s\" placeholder=\"%s\">\n",
                        type, name, placeholder));
            }
        }
        formBuilder.append("    <input type=\"submit\" value=\"Send\">\n</form>");

        FileObject file = processingEnv.getFiler()
                .createResource(StandardLocation.SOURCE_OUTPUT, "", fileName);
        try (PrintWriter out = new PrintWriter(file.openWriter())) {
            out.println(formBuilder);
        }
    }
}