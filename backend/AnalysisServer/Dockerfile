FROM python:3

RUN apt-get -y update
RUN apt-get -y install vim

RUN mkdir /usr/src/app
ADD . /usr/src/app
WORKDIR /usr/src/app

## Install packages
COPY requirements.txt ./
RUN pip install --upgrade pip
RUN pip install -r requirements.txt

RUN python manage.py makemigrations
RUN python manage.py migrate

COPY . .

EXPOSE 8083
CMD ["python", "manage.py", "runserver", "0.0.0.0:8083"]
