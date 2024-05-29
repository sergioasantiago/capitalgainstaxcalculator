# Capital Gains Tax Calculator

This project calculates capital gains tax based on a series of buy and sell operations represented by JSON data.

The program receives lists as input, one per line, of stock market transactions in json format via standard input (
stdin ).

For each input line, the program must return a list containing the tax paid for each operation performed.

An input example can be found at [`example/input.txt`](example/input.txt).

## Libraries

This projects uses the following libraries:

1. [jackson-databind](https://github.com/FasterXML/jackson-databind): To parses the JSON input string and write the
   output as JSON.

2. [junit-jupiter-api](https://junit.org/junit5/docs/current/user-guide/): To create and run unit tests.

3. [lombok](https://projectlombok.org/): Used to reduce boilerplate code. e.g: getters/setters 

## Rounding decimals

This project rounds decimals using `half up` and 2 decimal places. To implement this, the
[BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html) java class was used with a predefined
scale of 2 and [`RoundingMode.HALF_UP`](https://docs.oracle.com/javase/8/docs/api/java/math/RoundingMode.html#HALF_UP).

## Requirements:

- Java 17+
- Maven (https://maven.apache.org/)

[Or build using docker](#running-with-docker)

## Building

```shell
make build
```

## Running

Expecting user input:

```shell
make run
```

Using an `input.txt` file:

```shell
make run < example/input.txt
```

### Running Tests

```shell
make test
```

## Running with Docker

Build the docker image:

```shell
docker build -t capitalgainstax .
```

Running docker expecting user input:

```shell
docker run --rm -i  capitalgainstax
```

Running docker using `input.txt` file:

```shell
docker run --rm -i  capitalgainstax < example/input.txt
```