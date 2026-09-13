# 02 — Problema

## 1. Problema general y problema específico

### Problema general
Los equipos de estudiantes que desarrollan proyectos de software presentan dificultades para planificar de manera ordenada las actividades necesarias, distribuir equitativamente las responsabilidades y establecer una secuencia lógica de trabajo que les permita cumplir con la fecha de entrega.

### Problema específico
La ausencia de una herramienta que integre dos capacidades complementarias:

1. **Transformar automáticamente** la información del proyecto (objetivos, requerimientos, tecnologías, restricciones y fecha de entrega) y las habilidades del equipo en una **propuesta de planificación personalizada**.
2. **Permitir el seguimiento del avance** mediante el control de dependencias entre tareas y la asignación clara de responsables.

En la práctica, los estudiantes planifican de forma manual y poco estructurada: no existe un vínculo formal entre "qué sabemos hacer" (habilidades) y "qué debemos hacer" (tareas), ni un mecanismo que impida iniciar tareas sin haber completado sus requisitos previos.

---

## 2. Descripción de la problemática

Cuando un equipo de estudiantes inicia el desarrollo de un proyecto de software, suele enfrentar las siguientes situaciones:

**Síntomas observados**

- **Distribución desigual de las responsabilidades:** el trabajo se reparte sin considerar las habilidades reales de cada integrante, concentrándose en unos y dejando a otros con poca carga.
- **Falta de claridad sobre las tareas de cada integrante:** no se define con precisión qué debe hacer cada persona ni qué se espera de su trabajo.
- **Desconocimiento del orden adecuado de las actividades:** no hay una secuencia clara de qué debe hacerse primero y qué puede hacerse en paralelo.
- **Inicio de tareas sin haber completado actividades previas necesarias:** se avanza sobre trabajo incompleto, lo que genera retrabajo.
- **Retrasos en el desarrollo:** la mala organización acumula demoras que se detectan tarde.
- **Dificultades para realizar el seguimiento del avance:** no hay una vista única y actualizada del estado del proyecto.
- **Problemas de comunicación respecto a los cambios realizados:** los cambios de tareas, fechas o responsables no se comunican oportunamente, generando descoordinación.

**Causas subyacentes**

- **Falta de experiencia en gestión de proyectos:** los estudiantes dominan lo técnico, pero no necesariamente las prácticas de planificación y seguimiento.
- **Asignación de tareas por criterios subjetivos:** se reparte el trabajo por afinidad o disponibilidad, sin analizar las habilidades frente a los requerimientos.
- **Ausencia de una fuente única de verdad:** la información del proyecto y su avance se dispersa en mensajes, documentos y conversaciones, lo que impide tener una visión consolidada.

**Consecuencias**

Estas dificultades derivan en entregas con retraso, conflictos internos, carga desequilibrada, retrabajo y una calidad final inferior a la esperada.

---

## 3. Justificación del proyecto

**Relevancia académica.** La planificación y gestión de proyectos es una competencia transversal en la formación de ingenieros de software. Colab no solo resuelve una necesidad concreta, sino que **educa** al equipo en una práctica profesional (desglose de actividades, dependencias, seguimiento y responsabilidad) mientras la aplica.

**Relevancia tecnológica.** El proyecto integra una aplicación real de **inteligencia artificial generativa (LLM)** para una tarea concreta y de valor: convertir información estructurada en una planificación viable. Esto permite explorar de forma práctica el diseño de *prompts*, la salida estructurada (JSON) y su validación.

**Relevancia práctica.** Ataca directamente una problemática cotidiana de los equipos de estudiantes: la desorganización. Al automatizar la propuesta inicial y controlar dependencias y responsabilidades, reduce fricciones, retrasos y descoordinación.

**Viabilidad.** El alcance está acotado a un **MVP realista** (proyectos de software, notificaciones in-app, autenticación por correo, una sola llamada a la IA) y se apoya en tecnologías consolidadas y ampliamente documentadas (Android/Kotlin/Compose, React, Spring Boot, Django, PostgreSQL). Esto lo hace alcanzable para un equipo de 3 estudiantes en un periodo académico.

**Valor diferencial.** A diferencia de las herramientas de gestión genéricas (que exigen construir la planificación a mano), Colab **genera un punto de partida personalizado** según el proyecto y el equipo, que luego el grupo revisa, ajusta y aprueba.
