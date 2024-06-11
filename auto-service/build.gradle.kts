import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import wing.publishJava5hmlA

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(wings.plugins.android)
//    signing
    id("signing")
    id("maven-publish")
}

kotlin {
    // Or shorter:
    jvmToolchain(18)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_18)
        freeCompilerArgs.add("-Xcontext-receivers")
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
    }
}

//https://kotlinlang.org/docs/ksp-incremental.html#aggregating-vs-isolating
dependencies {
//    implementation("com.squareup:kotlinpoet-ksp:1.16.0")
    implementation(libs.ksp.process.api)
    // https://mvnrepository.com/artifact/com.google.auto.service/auto-service
    implementation(libs.google.auto.service.anno)
}

group = "io.github.5hmla"
version = "0.0.4"

val projectName = name

publishJava5hmlA("ksp library for Google AutoService 🚀")

tasks.register<Zip>("zipForPublish") {
    group = "5hmlA"
    dependsOn(tasks["publishSparkPublicationToLocalRepoRepository"])
    archiveBaseName = projectName
    destinationDirectory.set(file("repos"))
    from("repos") {
        include("**/*")
    }
    into("repos")
}

signing {
    sign(tasks["zipForPublish"])
}

//KSFile
//  packageName: KSName
//  fileName: String
//  annotations: List<KSAnnotation>  (File annotations)
//  declarations: List<KSDeclaration>
//    KSClassDeclaration // class, interface, object
//      simpleName: KSName
//      qualifiedName: KSName
//      containingFile: String
//      typeParameters: KSTypeParameter
//      parentDeclaration: KSDeclaration
//      classKind: ClassKind
//      primaryConstructor: KSFunctionDeclaration
//      superTypes: List<KSTypeReference>
//      // contains inner classes, member functions, properties, etc.
//      declarations: List<KSDeclaration>
//    KSFunctionDeclaration // top level function
//      simpleName: KSName
//      qualifiedName: KSName
//      containingFile: String
//      typeParameters: KSTypeParameter
//      parentDeclaration: KSDeclaration
//      functionKind: FunctionKind
//      extensionReceiver: KSTypeReference?
//      returnType: KSTypeReference
//      parameters: List<KSValueParameter>
//      // contains local classes, local functions, local variables, etc.
//      declarations: List<KSDeclaration>
//    KSPropertyDeclaration // global variable
//      simpleName: KSName
//      qualifiedName: KSName
//      containingFile: String
//      typeParameters: KSTypeParameter
//      parentDeclaration: KSDeclaration
//      extensionReceiver: KSTypeReference?
//      type: KSTypeReference
//      getter: KSPropertyGetter
//        returnType: KSTypeReference
//      setter: KSPropertySetter
//        parameter: KSValueParameter